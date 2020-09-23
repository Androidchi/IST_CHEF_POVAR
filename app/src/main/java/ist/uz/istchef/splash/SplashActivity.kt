package ist.uz.istchef.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import ist.uz.istchef.R
import ist.uz.istchef.screen.auth.SignActivity
import ist.uz.istchef.screen.main.MainActivity
import ist.uz.istchef.utils.Prefs
import ist.uz.personalstore.base.BaseActivity
import ist.uz.personalstore.base.startClearTopActivity

class SplashActivity : BaseActivity() {
    override fun getLayout(): Int=R.layout.activity_splash

    val compositeDisposable = CompositeDisposable()

    override fun initViews() {
        if (Prefs.getToken().isNullOrEmpty()){
            startClearTopActivity<SignActivity>()
        }   else{
            startClearTopActivity<MainActivity>()
            finish()
        }
    }

    override fun loadData() {
    }

    override fun initData() {
    }

    override fun updateData() {
    }
    
}