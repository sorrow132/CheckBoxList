package yuresko.checkboxlist

import io.reactivex.Observable

class Repository {

    private val listOfItems: List<Item> = listOf(
        Item(false, "one"),
        Item(false, "two"),
        Item(false, "thee"),
        Item(false, "Димон заебал пошли в футбик"),
        Item(false, "five"),
        Item(false, "six"),
        Item(false, "seven"),
        Item(false, "eight"),
        Item(false, "nine"),
        Item(false, "ten")
    )

    fun getItems(): Observable<List<Item>> {
        return Observable.create { emitter ->
            emitter.onNext(listOfItems)
        }
    }
}