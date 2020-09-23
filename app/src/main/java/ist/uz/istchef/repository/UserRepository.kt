package ist.uz.istchef.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ist.uz.istchef.api.BaseResponse
import ist.uz.istchef.api.CallbackWrapper
import ist.uz.istchef.model.*
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.utils.Prefs
import org.greenrobot.eventbus.EventBus

class UserRepository : BaseRepository() {

    fun getOrders(status: String, progress: MutableLiveData<Boolean>, error: MutableLiveData<String>, success: MutableLiveData<List<OrderFoodModel>>) {
        compositeDisposible.add(api.getOrders(status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : CallbackWrapper<BaseResponse<List<OrderFoodModel>?>>(error) {
                override fun onSuccess(t: BaseResponse<List<OrderFoodModel>?>) {
                    if (t.status) {
                        success.value = t.data
                    } else {
                    error.value=t.message ?: ""
                    }
                }
            })
        )
    }
    fun orderFoodProcess(id: Int, progress: MutableLiveData<Boolean>, error: MutableLiveData<String>, success: MutableLiveData<Boolean>) {
        compositeDisposible.add(api.orderFoodProcess(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : CallbackWrapper<BaseResponse<Any?>>(error) {
                override fun onSuccess(t: BaseResponse<Any?>) {
                    if (t.status) {
                        success.value = true
                    } else {
                        error.value=t.message ?: ""
                    }
                }
            })
        )
    }
    fun orderFoodComplete(id: Int, progress: MutableLiveData<Boolean>, error: MutableLiveData<String>, success: MutableLiveData<Boolean>) {
        compositeDisposible.add(api.orderFoodComplete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : CallbackWrapper<BaseResponse<Any?>>(error) {
                override fun onSuccess(t: BaseResponse<Any?>) {
                    if (t.status) {
                        success.value = true
                    } else {
                        error.value=t.message ?: ""
                    }
                }
            })
        )
    }

    fun getUser(progress: MutableLiveData<Boolean>, error: MutableLiveData<String>, success: MutableLiveData<UserModel>){
        compositeDisposible.add(api.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: CallbackWrapper<BaseResponse<UserModel?>>(error){
                override fun onSuccess(t: BaseResponse<UserModel?>) {
                    if (t.status){
                        success.value = t.data
                        Prefs.setUser(t.data!!)
                    }else{
                        if (t.error_code == 1){
                            EventBus.getDefault().post(EventModel(Constants.EVENT_LOGOUT, 0))
                        }
                        error.value = t.message
                    }
                }
            })
        )
    }
}