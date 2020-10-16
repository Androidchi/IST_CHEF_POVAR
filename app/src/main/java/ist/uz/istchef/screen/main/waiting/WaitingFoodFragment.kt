package ist.uz.istchef.screen.main.waiting

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ist.uz.istchef.R
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.model.OrderFoodModel
import ist.uz.istchef.model.ProductModel
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.view.view.adapter.OrderFoodsAdapter
import ist.uz.istchef.view.view.adapter.OrderFoodsAdapterListener
import ist.uz.personalstore.base.BaseFragment
import ist.uz.personalstore.base.showError
import kotlinx.android.synthetic.main.waiting_order_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import ist.uz.istchef.screen.main.waiting.witingbottom.WitingBottomFragment
import ist.uz.istchef.screen.main.waiting.witingbottom.WitingBottomFragmentListener

class WaitingFoodFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {
    override fun getLayout(): Int = R.layout.waiting_order_fragment
    lateinit var viewModel: MainViewModel
    lateinit var item: ProductModel

    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer{
            getBaseActivity { bases->
                bases.setProgress(it)
            }
            // swpRefresh.isRefreshing=it
        })

        viewModel.error.observe(this, Observer {
            activity?.showError(it)
        })

        viewModel.orderFoodsSending.observe(this, Observer {
          setData()
        })

        viewModel.orderFoodProcessData.observe(this, Observer {
            EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE,0))
            loadData()
        })

        viewModel.foodCancel.observe(this, Observer {
            getBaseActivity {base->
                base.setProgress(it)
                loadData()
            }
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
                override fun onClickBtn(item: OrderFoodModel) {
                    viewModel.orderFoodProcess(item.id)
                }
                override fun onClickItem(item:Any?) {
                    if (item is OrderFoodModel){
                        val fragment=WitingBottomFragment(object :WitingBottomFragmentListener{
                            override fun onDismiss() {
                                loadData()
                            }
                        })
                        fragment.item=item
                        fragment.show(activity!!.supportFragmentManager,fragment.tag)
                    }
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
        if (event.event == Constants.EVENT_UPDATE_CHEF) {
            activity?.runOnUiThread {
                viewModel.getSendingOrders()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        recycler.adapter?.notifyDataSetChanged()
    }
}