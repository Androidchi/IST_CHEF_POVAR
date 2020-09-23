package ist.uz.istchef.screen.auth

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.inputmask.MaskedTextChangedListener
import ist.uz.istchef.R
import ist.uz.istchef.screen.main.MainActivity
import ist.uz.istchef.utils.Prefs
import ist.uz.personalstore.base.BaseActivity
import ist.uz.personalstore.base.showError
import ist.uz.personalstore.base.startClearActivity
import ist.uz.personalstore.base.startClearTopActivity
import kotlinx.android.synthetic.main.activity_sign.*

class SignActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_sign
    lateinit var viewModel: SignViewModel
    var phoneListener: MaskedTextChangedListener? = null

    override fun initViews() {
        viewModel = ViewModelProvider(this).get(SignViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            setProgress(it)
        })

        viewModel.error.observe(this, Observer {
            showError(it)
        })

        viewModel.loginResponseConfirm.observe(this, Observer {
            if (it != null){
            Prefs.setToken(it.access_token)
            startClearActivity<MainActivity>()
                finish()
            }
        })

        phoneListener = MaskedTextChangedListener(
            "+998 ([00]) [000] [00] [00]",
            true,
            edPhone,
            null,
            null
        )
        edPhone.addTextChangedListener(phoneListener)
        edPhone.onFocusChangeListener = phoneListener

        cardViewSign.setOnClickListener {
            val phone=edPhone.text.toString().trim().replace("","").replace("(","").replace(")","").replace("\\s".toRegex(),"")
        viewModel.login(phone,edPassword.text.toString())
        }
    }

    override fun loadData() {
    }

    override fun initData() {
    }

    override fun updateData() {
    }

}