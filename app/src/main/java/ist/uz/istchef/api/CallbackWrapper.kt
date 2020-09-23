package ist.uz.istchef.api

import android.util.MalformedJsonException
import androidx.lifecycle.MutableLiveData
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import rx.exceptions.CompositeException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class CallbackWrapper<T>(private val error: MutableLiveData<String>) : DisposableObserver<T>() {
    override fun onComplete() {
    }

    protected abstract fun onSuccess(t: T)

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {

        when (e) {
            is HttpException -> {
                when (e.code()) {
                    503 -> error.value = "ServerMaintainanceError"
                    401 -> error.value = "UserNotFount"
                    else -> {
                        val responseBody = e.response()?.errorBody()
                        error.value = responseBody?.string()
                    }
                }
            }
            is MalformedJsonException -> error.value="MalfromedJsonException"
            is SocketTimeoutException -> error.value="timeout"
            is IOException -> error.value = "network error"
            is UnknownHostException -> error.value = "Незащищенное соединение"
            is KotlinNullPointerException -> error.value = "KotlinNullPointerException"
            is CompositeException -> {
                if (e.exceptions.size > 0){
                    var innerException = e.exceptions[0]
                    onError(innerException)
                }
            }
            else ->{
                error.value = "unknown error"
            }
        }
    }
}