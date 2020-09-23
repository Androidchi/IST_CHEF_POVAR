package ist.uz.istchef.model

import java.io.Serializable

data class UserInfoModel(
    val id:String,
    val fullname:String,
    val access_token:String
):Serializable