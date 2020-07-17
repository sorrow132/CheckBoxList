package yuresko.checkboxlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject

interface IViewModel {

    val items: LiveData<List<Item>>

    fun changeCheckBox(index: Int)
}

class TestViewModel(private val repository: Repository) : ViewModel(),
    IViewModel {

    private var trigger: Boolean = true

    private val rxDiscountEnabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val rxCheckBoxModify: BehaviorSubject<Map<Int, Boolean>> =
        BehaviorSubject.createDefault(
            emptyMap()
        )

    override val items: MutableLiveData<List<Item>> = MutableLiveData()

    init {
        Observable
            .combineLatest(
                rxCheckBoxModify,
                rxDiscountEnabled,
                repository.getItems(),
                Function3 { rxCheckBoxModify: Map<Int, Boolean>, rxDiscountEnabled: Boolean, items: List<Item> ->
                    Triple(rxCheckBoxModify, rxDiscountEnabled, items)
                }
            ).map { (checkBoxModify, isDiscountEnabled, items) ->
                items.mapIndexed { index, item ->
                    if (isDiscountEnabled && checkBoxModify.containsKey(index)) {
                        trigger = false
                        item.copy(isChosen = checkBoxModify[index]!!)
                    } else if (checkBoxModify.containsKey(index)) {
                        trigger = true
                        item.copy(isChosen = checkBoxModify[index]!!)
                    } else {
                        item
                    }
                }
            }.subscribe {
                items.postValue(it)
            }
    }

    override fun changeCheckBox(index: Int) {
        val firstMap = rxCheckBoxModify.value?.toMutableMap() ?: HashMap()

        firstMap[index] = false

        val map = rxCheckBoxModify.value?.toMutableMap() ?: HashMap()
        map[index] = trigger
        rxCheckBoxModify.onNext(map)

        if (map[index] == true) {
            rxDiscountEnabled.onNext(true)
        } else {
            rxDiscountEnabled.onNext(false)
        }
    }
}