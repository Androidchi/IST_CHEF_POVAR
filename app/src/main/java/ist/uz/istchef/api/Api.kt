package ist.uz.istchef.api

import io.reactivex.Observable
import ist.uz.istchef.model.*
import ist.uz.istchef.utils.Prefs
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("login")
    fun login(@Body request: LoginRequest): Observable<BaseResponse<UserInfoModel?>>

    @GET("get-user")
    fun getUser(@Query("fcm_token")fcmToken:String=Prefs.getFCM() ?: ""):Observable<BaseResponse<UserModel?>>

    @GET("get-order-food")
    fun getOrders(@Query("status")status: String):Observable<BaseResponse<List<OrderFoodModel>?>>

    @GET("order-food-process")
    fun orderFoodProcess(@Query("order_food_id") orderFoodId: Int): Observable<BaseResponse<Any?>>

    @GET("order-food-complete")
    fun orderFoodComplete(@Query("order_food_id")orderFoodId:Int):Observable<BaseResponse<Any?>>

    @GET("get-my-foods")
    fun getMyFoods():Observable<BaseResponse<List<ProductModel>?>>

}