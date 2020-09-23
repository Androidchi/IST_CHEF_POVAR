package ist.uz.istchef.screen.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ist.uz.istchef.model.UserInfoModel
import ist.uz.istchef.repository.AuthRepository

class SignViewModel : ViewModel() {

    val authRepository = AuthRepository()

    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val loginResponseConfirm = MutableLiveData<UserInfoModel?>()

    fun login(number: String, password: String) {
        authRepository.loginUser(number, password, progress, error, loginResponseConfirm)
    }
}