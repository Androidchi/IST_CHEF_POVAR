package ist.uz.istchef.screen.main.order

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ist.uz.istchef.R
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.istchef.view.view.adapter.ProductFoodsAdapter
import ist.uz.personalstore.base.BaseFragment
import ist.uz.personalstore.base.showError
import kotlinx.android.synthetic.main.fragment_order_foods.*


class OrderFoodsFragment : BaseFragment() {
    override fun getLayout(): Int=R.layout.fragment_order_foods
    lateinit var viewModel: MainViewModel
    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            getBaseActivity { baseActivity ->
                baseActivity.setProgress(it)
            }
        })
        viewModel.error.observe(this, Observer {
            getBaseActivity {base ->
                base.showError(it)
            }
        })

        viewModel.getOrders.observe(this, Observer {
            setData()
        })
    }

    override fun loadData() {
        viewModel.getMyOrders()
    }

    override fun setData() {
        if (viewModel.getOrders.value != null){
            recycler.layoutManager=LinearLayoutManager(activity)
            recycler.adapter=ProductFoodsAdapter(viewModel.getOrders.value!!)

        }
    }

}