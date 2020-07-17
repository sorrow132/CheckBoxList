package yuresko.checkboxlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import yuresko.checkboxlist.Item
import yuresko.checkboxlist.R

class CustomViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false
        )
    ) {
    private var checkBox: CheckBox? = itemView.findViewById(R.id.checkBox)

    fun bind(item: Item) {
        checkBox?.isChecked = item.isChosen
        checkBox?.text = item.name
    }
}