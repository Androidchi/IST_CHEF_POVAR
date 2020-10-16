package ist.uz.istchef.screen.main.proccess

import android.os.Bundle
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
import kotlinx.android.synthetic.main.proccessing_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ProccessingFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun getLayout(): Int = R.layout.proccessing_fragment
    lateinit var viewModel: MainViewModel
    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, {
            swpRefreshProcces.isRefreshing = it
        })
        viewModel.error.observe(this, {
            activity?.showError(it)
        })

        viewModel.orderFoodsProcessing.observe(this, {
           setData()
        })

        viewModel.orderFoodProcessData.observe(this,{
            loadData()
        })

        swpRefreshProcces.setOnRefreshListener(this)
    }


    override fun loadData() {
        viewModel.getProcessingOrders()

    }

    override fun setData() {
        swpRefreshProcces.isRefreshing = false
        if (viewModel.orderFoodsProcessing.value != null) {
            recyclerProcces.layoutManager = LinearLayoutManager(activity)
            recyclerProcces.adapter = OrderFoodsAdapter(viewModel.orderFoodsProcessing.value!!, object: OrderFoodsAdapterListener{
                override fun onClickBtn(item: OrderFoodModel) {
                    viewModel.orderFoodComplete(item.id)
                }

                override fun onClickItem(items: Any?) {

                }
            })
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
        if (event.event == Constants.EVENT_UPDATE) {
            loadData()
        }
    }



}