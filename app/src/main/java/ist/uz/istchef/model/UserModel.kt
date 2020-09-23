package ist.uz.istchef.model

data class UserModel(
    val id: Int,
    val telephone: String,
    val fullname: String,
    val comment: String,
    val active: Int
)