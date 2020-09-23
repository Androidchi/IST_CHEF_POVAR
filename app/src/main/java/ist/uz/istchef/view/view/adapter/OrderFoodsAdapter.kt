package ist.uz.istchef.view.view.adapter

import android.view.View
import ist.uz.istchef.R
import ist.uz.istchef.model.OrderFoodModel
import ist.uz.personalstore.base.getColor
import kotlinx.android.synthetic.main.proccesing_itemadapter.view.*

interface OrderFoodsAdapterListener {
    fun onClick(item: OrderFoodModel)
}

class OrderFoodsAdapter(val list: List<OrderFoodModel>, val handler: OrderFoodsAdapterListener) :
    BaseAdapter(list.toMutableList(), R.layout.proccesing_itemadapter) {

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = list[position]
        holder.itemView.tv_orderid.text = item.id.toString()
        holder.itemView.tv_comment.text = item.comment
        holder.itemView.tv_count.text = item.count.toString()
        holder.itemView.tv_date.text = item.update_time
        holder.itemView.tv_person.text = item.waiter
        holder.itemView.tv_table.text = item.table
        holder.itemView.tv_food_name.text = item.food_name

        holder.itemView.btnCart.setOnClickListener {
            handler.onClick(item)
        }

        if (item.status == "completed") {
            holder.itemView.btnCart.visibility = View.GONE
        } else if (item.status == "processing") {
            holder.itemView.btnCart.setCardBackgroundColor(holder.itemView.getColor(R.color.yellow))
        } else if (item.comment != "") {
            holder.itemView.tv_comment.visibility = View.VISIBLE
        }
    }


}