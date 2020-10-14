package ist.uz.istchef.view.view.adapter

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import ist.uz.istchef.R
import ist.uz.istchef.model.ProductModel
import ist.uz.personalstore.base.loadImage
import kotlinx.android.synthetic.main.product_foods_adapter.view.*

class ProductFoodsAdapter(items:List<ProductModel>) : BaseAdapter(items.toMutableList(),R.layout.product_foods_adapter) {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item=items[position] as ProductModel

        holder.itemView.imgProduct.loadImage(item.picture)
        holder.itemView.tvProductName.text = item.name
        holder.itemView.tvProductPrice.text =item.price.toString()

        if (item.is_have!=true){
        holder.itemView.tvStatus.text=holder.itemView.context.getString(R.string.status)
            holder.itemView.cardViewStatus.setCardBackgroundColor(holder.itemView.context.getColor(R.color.red))
        }

    }
}