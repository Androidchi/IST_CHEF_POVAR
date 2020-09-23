package ist.uz.istchef.model

import java.io.Serializable

data class OrderFoodModel(
    val id:Int,
    val order_id:String,
    val table:String,
    val food_name:String,
    val unit:String,
    val waiter:String,
    val count:Int,
    val status:String,
    val comment:String,
    val update_time:String
): Serializable