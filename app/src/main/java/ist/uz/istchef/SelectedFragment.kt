package ist.uz.istchef

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ist.uz.istchef.adapter.SelectedAdapter
import ist.uz.istchef.adapter.SelectedListener
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.model.NewOrderModel
import ist.uz.istchef.utils.Constants
import ist.uz.istchef.utils.Prefs
import ist.uz.personalstore.base.BaseFragment
import kotlinx.android.synthetic.main.selected_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SelectedFragment : BaseFragment(), SelectedListener {
    override fun getLayout(): Int=R.layout.selected_fragment
    lateinit var item:NewOrderModel
    var adapter:SelectedAdapter?=null

    val items= listOf(
        NewOrderModel("1","1 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 16:05","Alijon 1-stol"),
        NewOrderModel("2","2 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 16:00","Alijon 1-stol"),
        NewOrderModel("3","5 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 12:00","Alijon 1-stol"),
        NewOrderModel("4","1 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 14:00","G`anijon 3-stol"),
        NewOrderModel("5","Sho`rva mol go`shtli","Yog`liroq qilib  qazi bilan","03.09.2020 16:00","Valijon 2-stol")
    )
    override fun setupViews() {
        setItems()
          }

    fun setItems(){
        recycler.layoutManager=LinearLayoutManager(activity)
        adapter= SelectedAdapter(items,this)
        recycler.adapter=adapter

    }

    override fun loadData() {

    }

    override fun setData() {
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
    fun onEvent(event: EventModel<Int>){
        if (event.event== Constants.EVENT_UPDATE){
           setupViews()
            setItems()
        }
    }

    override fun onUpdateItem() {

    }

}