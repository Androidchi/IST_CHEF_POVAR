package ist.uz.istchef.adapter

import ist.uz.istchef.R
import ist.uz.istchef.SelectedFragment
import ist.uz.istchef.model.NewOrderModel
import kotlinx.android.synthetic.main.selected_item_adapter.view.*

interface SelectedListener{
fun onUpdateItem()
}
class SelectedAdapter(val list: List<NewOrderModel>,listener:SelectedListener):BaseAdapter(list.toMutableList(), R.layout.selected_item_adapter) {
lateinit var  item:NewOrderModel
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item=list[position]
        holder.itemView.name.text=item.kilogram
        holder.itemView.date.text=item.date

    }
}