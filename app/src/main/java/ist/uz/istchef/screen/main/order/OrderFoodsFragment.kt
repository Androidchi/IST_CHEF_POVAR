package ist.uz.istchef.screen.main.order

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ist.uz.istchef.R
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.model.ProductModel
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.istchef.screen.main.order.add2cart.OrderBottomFragment
import ist.uz.istchef.screen.main.order.add2cart.OrderBottomFragmentListener
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.view.view.adapter.BaseAdapterListener
import ist.uz.istchef.view.view.adapter.ProductFoodsAdapter
import ist.uz.personalstore.base.BaseFragment
import ist.uz.personalstore.base.showError
import kotlinx.android.synthetic.main.fragment_order_foods.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class OrderFoodsFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {
    override fun getLayout(): Int=R.layout.fragment_order_foods
    lateinit var viewModel: MainViewModel
    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
           swpRefresh.isRefreshing=it
        })

        viewModel.foodHave.observe(this, Observer {
            getBaseActivity { base->
                base.setProgress(it)
                loadData()
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

        swpRefresh.setOnRefreshListener(this)
    }

    override fun loadData() {
        viewModel.getMyOrders()
    }

    override fun setData() {
        swpRefresh.isRefreshing=false
        if (viewModel.getOrders.value != null){
            recycler.layoutManager=LinearLayoutManager(activity)
            recycler.adapter=ProductFoodsAdapter(viewModel.getOrders.value!! ?: emptyList(),object :BaseAdapterListener{
                override fun onClickItem(item: Any?) {
                    if (item is ProductModel){
                        showAddOrder(item)
                    }
                }

            })

        }
    }

    fun showAddOrder(item: ProductModel){
        getBaseActivity { base ->
            val fragment = OrderBottomFragment.newInstance(item, object : OrderBottomFragmentListener {
                override fun onDismiss() {
                    loadData()
                }
            })
            fragment.show(base.supportFragmentManager, fragment.tag)
        }
    }

    override fun onRefresh() {
        loadData()
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
    fun onEvent(event: EventModel<Any>) {
        if (event.event == Constants.EVENT_UPDATE_PRODUCTS) {
            activity?.runOnUiThread {
               loadData()
            }
        }
    }
}