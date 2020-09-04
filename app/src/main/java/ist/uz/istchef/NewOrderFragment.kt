package ist.uz.istchef

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ist.uz.istchef.adapter.NewOrderAdapter
import ist.uz.istchef.model.NewOrderModel
import ist.uz.personalstore.base.BaseFragment
import kotlinx.android.synthetic.main.new_order_fragment.*

class NewOrderFragment : BaseFragment() {
    override fun getLayout(): Int =R.layout.new_order_fragment

        val items= listOf(
            NewOrderModel("1","1 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 16:05","Alijon 1-stol"),
            NewOrderModel("2","2 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 16:00","Alijon 1-stol"),
            NewOrderModel("3","5 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 12:00","Alijon 1-stol"),
            NewOrderModel("4","1 kilogram toshkent osh","Yog`liroq qilib bedan tuxum va qazi bilan","03.09.2020 14:00","G`anijon 3-stol"),
            NewOrderModel("5","Sho`rva mol go`shtli","Yog`liroq qilib  qazi bilan","03.09.2020 16:00","Valijon 2-stol")
        )
    override fun setupViews() {
        recycler.layoutManager=LinearLayoutManager(activity)
        recycler.adapter=NewOrderAdapter(items)

    }

    override fun loadData() {
        }

    override fun setData() {
        }

}