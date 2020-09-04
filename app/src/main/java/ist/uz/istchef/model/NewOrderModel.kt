package ist.uz.istchef.model

import java.io.Serializable

data class NewOrderModel(
    var id:String,
    val kilogram:String,
    var comment:String,
    var date:String,
    val person:String
):Serializable