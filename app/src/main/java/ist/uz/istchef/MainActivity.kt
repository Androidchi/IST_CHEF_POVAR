package ist.uz.istchef

import android.view.MenuItem
import ist.uz.istchef.utils.Constants
import ist.uz.personalstore.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    var newOrderFragment = NewOrderFragment()
    var selectedFragment = SelectedFragment()
    override fun initViews() {

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
                else -> true
            }
        }
        if (intent.hasExtra((Constants.EXTRA_DATA))){
            nav_bottom.selectedItemId=intent.getIntExtra(Constants.EXTRA_DATA,R.id.newOrderfragment)
        }else{
            pushFragment(R.id.container,newOrderFragment,newOrderFragment.tag ?: "")
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun loadData() {
    }

    override fun initData() {
    }

    override fun updateData() {
    }

}