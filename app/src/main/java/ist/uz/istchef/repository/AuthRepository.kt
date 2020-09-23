package ist.uz.istchef.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ist.uz.istchef.api.BaseResponse
import ist.uz.istchef.api.CallbackWrapper
import ist.uz.istchef.model.LoginRequest
import ist.uz.istchef.model.UserInfoModel

class AuthRepository : BaseRepository() {

    fun loginUser(number: String, password: String, progress: MutableLiveData<Boolean>, error: MutableLiveData<String>,
        success: MutableLiveData<UserInfoModel?>) {
        compositeDisposible.clear()
        val request = LoginRequest(number, password)
        compositeDisposible.add(api.login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.value = false }
            .doOnSubscribe { progress.value = true }
            .subscribeWith(object : CallbackWrapper<BaseResponse<UserInfoModel?>>(error) {
                override fun onSuccess(t: BaseResponse<UserInfoModel?>) {
                    if (t.status) {
                        success.value = t.data
                    } else {
                        error.value = t.message
                    }
                }
            })
        )
    }
}