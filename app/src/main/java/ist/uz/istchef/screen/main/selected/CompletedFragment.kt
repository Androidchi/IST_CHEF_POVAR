package ist.uz.istchef.screen.main.selected

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ist.uz.istchef.R
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.model.OrderFoodModel
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.view.view.adapter.OrderFoodsAdapter
import ist.uz.istchef.view.view.adapter.OrderFoodsAdapterListener
import ist.uz.personalstore.base.BaseFragment
import ist.uz.personalstore.base.showError
import kotlinx.android.synthetic.main.completed_fragment.*
import kotlinx.android.synthetic.main.orders_food_item_adapter.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class CompletedFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun getLayout(): Int = R.layout.completed_fragment
    lateinit var viewModel: MainViewModel
    var adapter:OrderFoodsAdapter?=null

    override fun setupViews() {
        viewModel = ViewModelProvider(this!!.activity!!).get(MainViewModel::class.java)

        viewModel.progress.observe(this, {
            swpRefreshCompleted.isRefreshing = it
        })

        viewModel.error.observe(this, {
            activity?.showError(it)
        })

        viewModel.orderFoodsCompleted.observe(this,{
            setFoodCompleted()
        })

        swpRefreshCompleted.setOnRefreshListener(this)

    }

    fun setFoodCompleted() {
        swpRefreshCompleted.isRefreshing=false
        if (viewModel.orderFoodsCompleted.value !=null){
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = OrderFoodsAdapter(viewModel.orderFoodsCompleted.value!!,object :OrderFoodsAdapterListener{
                override fun onClickBtn(item: OrderFoodModel) {
                }

                override fun onClickItem(item: Any?) {

                }

            })
        }

    }

    override fun loadData() {
        viewModel.getCompletedOrders()
    }

    override fun setData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(event: EventModel<Int>) {
        if (event.event == Constants.EVENT_UPDATE) {
            setupViews()

        }
    }

    override fun onRefresh() {
        loadData()
    }

}