package ist.uz.personalstore.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import ist.uz.istchef.view.view.ProgressDialogFragment

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getLayout(): Int
    abstract fun initViews()
    abstract fun loadData()
    abstract fun initData()
    abstract fun updateData()
    var progressDialogFragment: ProgressDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initViews()
        loadData()
        initData()
    }

//   fun onClickCartButton() {
//        startClearActivity<MainActivity>(Constants.EXTRA_DATA, R.id.cartFragment)
//        finish()
//    }


    open fun updateStore(){

    }

    fun hideFragments() {
        supportFragmentManager.fragments.forEach {
            if (it.isAdded && it.isVisible) {
                supportFragmentManager.beginTransaction()
                    .hide(it)
                    .commitAllowingStateLoss()
            }
        }
    }

    fun setProgress(inProgress: Boolean) {
        if (inProgress) {
            progressDialogFragment = ProgressDialogFragment()
            progressDialogFragment?.isCancelable = false
            progressDialogFragment?.show(supportFragmentManager, progressDialogFragment!!.tag)
        } else {
            progressDialogFragment?.dismiss()
        }
    }

    fun popFragmentsWhile(tag: String) {
        supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun pushFragmentWithoutHistory(layoutId: Int,fragment: BaseFragment,tag: String){
            hideKeyboard()
        supportFragmentManager
            .beginTransaction()
            .add(layoutId,fragment)
            .commit()
    }
//
//    fun pushBtmToTopFragment(layoutId: Int,fragment: BaseFragment,tag: String){
//        hideKeyboard()
//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up)
//            .add(layoutId,fragment)
//            .addToBackStack("")
//            .commit()
//    }
//
//    fun pushRightToLeftFragment(layoutId: Int, fragment: BaseFragment, tag: String) {
//        hideKeyboard()
//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
//            .add(layoutId, fragment)
//            .addToBackStack(tag)
//            .commit()
//
//     }

//    fun pushFadeInToFadeOut(layoutId: Int, fragment: BaseFragment, tag: String) {
//        hideKeyboard()
//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//            .add(layoutId, fragment)
//            .addToBackStack(tag)
//            .commit()
//
//    }

    fun pushFragment(layoutId: Int, fragment: BaseFragment, tag: String) {
        hideKeyboard()
        supportFragmentManager
            .beginTransaction()
            .add(layoutId, fragment)
            .addToBackStack("")
            .commit()

    }

    fun pushFragment(layoutId: Int, fragment: BaseFragment, tag: String,
        args: Bundle,
        status: String) {
        hideKeyboard()
        fragment.arguments = args
        supportFragmentManager
            .beginTransaction()
            .add(layoutId, fragment)
            .addToBackStack(tag)
            .commit()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus

        view?.clearFocus()
        view?.isSelected = false

        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(LocaleManager.setLocale(newBase))
//    }
}