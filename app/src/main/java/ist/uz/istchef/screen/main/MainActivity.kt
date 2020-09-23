package ist.uz.istchef.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import ist.uz.istchef.BuildConfig
import ist.uz.istchef.R
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.screen.main.aboutapp.AboutAppActivity
import ist.uz.istchef.screen.main.proccess.ProccessingFragment
import ist.uz.istchef.screen.main.selected.CompletedFragment
import ist.uz.istchef.screen.main.waiting.WaitingFoodFragment
import ist.uz.istchef.splash.SplashActivity
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.utils.Prefs
import ist.uz.personalstore.base.BaseActivity
import ist.uz.personalstore.base.startActivity
import ist.uz.personalstore.base.startClearActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_layout.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun getLayout(): Int = R.layout.activity_main
    lateinit var viewModel:MainViewModel

    var newOrderFragment = WaitingFoodFragment()
    var selectedFragment = CompletedFragment()
    var preparedFragment = ProccessingFragment()
    override fun initViews() {
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.userData.observe(this, Observer {
            setClientDataNav()
        })
        hideFragments()

        nav_bottom.setOnNavigationItemSelectedListener { item: MenuItem ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.newOrderfragment -> {
                    if (newOrderFragment.isAdded && newOrderFragment.isVisible) {

                    } else {
                        hideFragments()
                        if (!newOrderFragment.isAdded) {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.container, newOrderFragment)
                                .commitAllowingStateLoss()
                        } else {
                            supportFragmentManager.beginTransaction()
                                .show(newOrderFragment)
                                .commitAllowingStateLoss()
                        }
                    }
                    true
                }
                R.id.selectedfragment -> {
                    if (selectedFragment.isAdded && selectedFragment.isVisible) {

                    } else {
                        hideFragments()
                        if (!selectedFragment.isAdded) {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.container, selectedFragment)
                                .commitAllowingStateLoss()
                        } else {
                            supportFragmentManager.beginTransaction()
                                .show(selectedFragment)
                                .commitAllowingStateLoss()
                        }
                    }
                    true
                }
                R.id.preparedFragment -> {
                    if (preparedFragment.isAdded && preparedFragment.isVisible) {

                    } else {
                        hideFragments()
                        if (!preparedFragment.isAdded) {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.container, preparedFragment)
                                .commitAllowingStateLoss()
                        } else {
                            supportFragmentManager.beginTransaction()
                                .show(preparedFragment)
                                .commitAllowingStateLoss()
                        }
                    }
                    true
                }
                else -> true
            }
        }

        val toggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigation.setNavigationItemSelectedListener(this)

        imgMore.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }

        if (intent.hasExtra((Constants.EXTRA_DATA))) {
            nav_bottom.selectedItemId = intent.getIntExtra(
                Constants.EXTRA_DATA,
                R.id.newOrderfragment
            )
        } else {
            pushFragment(R.id.container, newOrderFragment, newOrderFragment.tag ?: "")
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun setClientDataNav() {
        val view = navigation.getHeaderView(0)
        val user = Prefs.getUser()
        view.tvPersonName.text = user?.fullname
        view.tvPhone.text = user?.telephone
    }

    override fun loadData() {
        viewModel.getUser()
    }

    override fun initData() {
    }

    override fun updateData() {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionShareApp) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT, "Друзья, я предлагаю вам это приложение: "
                        + getString(R.string.app_name)
                        + "\n" +
                        " https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            startActivity(Intent.createChooser(shareIntent,"Отправить своим друзьям."))
        }else if (item.itemId==R.id.actionLogout){
            Prefs.clearAll()
            startActivity<SplashActivity>()
            finish()
        }  else if (item.itemId==R.id.actionAboutUs){
            startActivity<AboutAppActivity>()
        }
        return true
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(event: EventModel<Any>){
        if (event.event == Constants.EVENT_LOGOUT){
            Prefs.clearAll()
            startClearActivity<SplashActivity>()
            finish()
        }
    }

}