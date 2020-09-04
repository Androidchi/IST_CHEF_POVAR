package ist.uz.istchef.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Toast
import ist.uz.istchef.R
import ist.uz.istchef.model.BasketModel
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.model.NewOrderModel
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.utils.Prefs
import kotlinx.android.synthetic.main.new_order_item.view.*
import org.greenrobot.eventbus.EventBus

class NewOrderAdapter(var list:List<NewOrderModel>):BaseAdapter(list.toMutableList(), R.layout.new_order_item) {
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item=list[position]
        val making:String="Tayyorlash"
        holder.itemView.orderid.text=item.id.toString()
        holder.itemView.person.text=item.person
        holder.itemView.comment.text=item.comment
        holder.itemView.kilogram.text=item.kilogram
        holder.itemView.date.text=item.date

        holder.itemView.btnCart.setOnClickListener {

            Prefs.add2Cart(BasketModel(item.id,item.date))
            holder.itemView.tvOrderItem.setText(making)
            holder.itemView.tvOrderItem.setTextColor(Color.BLACK)
            holder.itemView.btnCart.setCardBackgroundColor(Color.YELLOW)
            EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE, 0))
                Toast.makeText(holder.itemView.context,"Qo`shildi",position).show()

        }
    }

}