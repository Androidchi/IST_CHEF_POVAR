package ist.uz.istchef.screen.main.waiting

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ist.uz.istchef.R
import ist.uz.istchef.model.OrderFoodModel
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.istchef.view.view.adapter.OrderFoodsAdapter
import ist.uz.istchef.view.view.adapter.OrderFoodsAdapterListener
import ist.uz.personalstore.base.BaseFragment
import ist.uz.personalstore.base.showError
import kotlinx.android.synthetic.main.waiting_order_fragment.*

class WaitingFoodFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {
    override fun getLayout(): Int = R.layout.waiting_order_fragment
    lateinit var viewModel: MainViewModel

    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this,{
            swpRefresh.isRefreshing=it

        })


        viewModel.error.observe(this, Observer {
            activity?.showError(it)
        })

        viewModel.orderFoodsSending.observe(this, Observer {
          setData()
        })

        viewModel.orderFoodProcessData.observe(this, Observer {
            loadData()
        })


        swpRefresh.setOnRefreshListener(this)
    }

    override fun loadData() {
        viewModel.getSendingOrders()
    }

    override fun setData() {
        swpRefresh.isRefreshing = false
        if (viewModel.orderFoodsSending.value != null) {
            recycler.layoutManager = LinearLayoutManager(activity)
            recycler.adapter = OrderFoodsAdapter(viewModel.orderFoodsSending.value!!,object :OrderFoodsAdapterListener{
                override fun onClick(item: OrderFoodModel) {
                    viewModel.orderFoodProcess(item.id)
                }

            })
        }
    }

    override fun onRefresh() {
        loadData()
    }




}