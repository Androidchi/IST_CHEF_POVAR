package ist.uz.istchef.screen.main.order.addOrder

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ist.uz.istchef.R
import ist.uz.istchef.model.EventModel
import ist.uz.istchef.model.ProductModel
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.istchef.utils.Constants
import ist.uz.personalstore.base.formattedAmount
import ist.uz.personalstore.base.loadImage
import ist.uz.personalstore.base.showError
import ist.uz.personalstore.base.showToast
import kotlinx.android.synthetic.main.bottom_fragment_dialog.flLoading
import kotlinx.android.synthetic.main.order_fragment_dialog.*
import org.greenrobot.eventbus.EventBus

interface OrderBottomFragmentListener{
    fun onDismiss()
}
class OrderBottomFragment : BottomSheetDialogFragment() {
    lateinit var listener: OrderBottomFragmentListener
    lateinit var item: ProductModel
    lateinit var viewModel: MainViewModel

    companion object{
        fun newInstance(item: ProductModel, listener: OrderBottomFragmentListener) : OrderBottomFragment {
            val fragment = OrderBottomFragment()
            fragment.item = item
            fragment.listener = listener
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.order_fragment_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            flLoading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer {
            activity?.showError(it)
        })

        viewModel.foodHave.observe(this, Observer {
            listener.onDismiss()
            dismiss()
                // activity?.showToast(getString(R.string.saved_2_cart))
        })

        imgProduct.loadImage(item.picture)
        tvName.text=item.name
        tvPrice.text=item.price.formattedAmount()

        if (item.is_have!=true){
            tvStatus.text=context!!.getString(R.string.completed)
            cardViewStatus.setCardBackgroundColor(context!!.getColor(R.color.red))
            tvAdd2Cart.text=context!!.getString(R.string.order_make)
            cardViewCancle.setCardBackgroundColor(context!!.getColor(R.color.colorPrimary))
        }else{
            tvStatus.text=context!!.getString(R.string.status)
            cardViewStatus.setCardBackgroundColor(context!!.getColor(R.color.yellow))
            tvAdd2Cart.text=context!!.getString(R.string.order_cancel)
            cardViewCancle.setCardBackgroundColor(context!!.getColor(R.color.red))
            }

        cardViewCancle.setOnClickListener {
            if (item.is_have==false){
                viewModel.orderFoodHave(item.id,is_have = true)
                EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE_PRODUCTS,0))
            }else{
                viewModel.orderFoodHave(item.id,is_have=false)
                EventBus.getDefault().post(EventModel(Constants.EVENT_UPDATE_PRODUCTS,0))
            }

        }

    }
}