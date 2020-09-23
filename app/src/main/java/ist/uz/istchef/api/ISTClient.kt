package ist.uz.istchef.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import ist.uz.istchef.BuildConfig
import ist.uz.istchef.utils.Prefs
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object ISTClient {

    internal  var retrofit: Retrofit?= null
    fun intitClient(context: Context) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.123/kafe.loc/admin/api/chef/")
            .client(getOkHttpClient(context))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getInstanse(context: Context):Retrofit{
        if (retrofit != null){
            return retrofit!!
        }else{
            intitClient(context)
            return retrofit!!
        }
    }

    fun getOkHttpClient(context: Context): OkHttpClient {
        var builder = OkHttpClient().newBuilder()
        builder.retryOnConnectionFailure(false)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.followRedirects(false)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(ChuckInterceptor(context))
        }
        builder.addInterceptor(AppInterceptor())
        builder=enableTls12OnPreLolipop(builder)

        return builder.build()
    }

    private fun enableTls12OnPreLolipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT in 16..21) {
            try {
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.getTrustManagers()
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException(
                        "Unexpected default trust managers:" + Arrays.toString(
                            trustManagers
                        )
                    )
                }
                val trustManager = trustManagers[0] as X509TrustManager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(trustManager), null)
                val sslSocketFactory = sslContext.socketFactory
                return client.sslSocketFactory(sslSocketFactory, trustManager)
            } catch (e: Exception) {
                return client
            }
        } else {
            return client
        }
    }

    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            return chain.proceed(getRequest(original))
        }

        fun getRequest(original: Request): Request {
            var builder = original.newBuilder()
            builder.addHeader("Content-Type", "application/json")
            builder.header("Connection", "close")
            builder.header("X-Mobile-Type", "android")
            builder.addHeader("access_token",Prefs.getToken())
            builder.method(original.method(), original.body())
            return builder.build()
        }
    }
}