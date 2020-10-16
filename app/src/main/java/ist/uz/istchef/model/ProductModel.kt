package ist.uz.istchef.model

data class ProductModel(
    val id:Int,
    val name:String,
    val price:Double,
    val unit:String,
    val is_have:Boolean,
    val picture:String
)