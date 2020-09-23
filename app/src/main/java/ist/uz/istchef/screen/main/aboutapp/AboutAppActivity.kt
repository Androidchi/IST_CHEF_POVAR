package ist.uz.istchef.screen.main.aboutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ist.uz.istchef.BuildConfig
import ist.uz.istchef.R
import ist.uz.personalstore.base.BaseActivity
import ist.uz.personalstore.base.startActivityToOpenUrlInBrowser
import kotlinx.android.synthetic.main.activity_about_app.*

class AboutAppActivity : BaseActivity() {
    override fun getLayout(): Int=R.layout.activity_about_app

    override fun initViews() {
   tvVersion.text="version: ${BuildConfig.VERSION_NAME}"

        imgBack.setOnClickListener {
            finish()
        }
        lyDeveloper.setOnClickListener {
            startActivityToOpenUrlInBrowser("isti.uz")
        }
    }

    override fun loadData() {
    }

    override fun initData() {
    }

    override fun updateData() {
    }

}