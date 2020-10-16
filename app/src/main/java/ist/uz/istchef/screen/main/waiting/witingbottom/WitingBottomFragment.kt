package ist.uz.istchef.screen.main.waiting.witingbottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ist.uz.istchef.R
import ist.uz.istchef.model.OrderFoodModel
import ist.uz.istchef.screen.main.MainViewModel
import ist.uz.personalstore.base.*
import kotlinx.android.synthetic.main.bottom_fragment_dialog.*

interface WitingBottomFragmentListener{
    fun onDismiss()
}
class WitingBottomFragment(val listener:WitingBottomFragmentListener) : BottomSheetDialogFragment() {
    lateinit var item: OrderFoodModel
    lateinit var viewModel: MainViewModel


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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            flLoading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer {
            activity?.showError(it)
        })

        viewModel.foodCancel.observe(this, Observer {
            listener.onDismiss()
            dismiss()
            activity?.showToast(getString(R.string.saved_2_cart))
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // imgProduct.loadImage(item.picture)
        tvReceiptID.text=item.order_id
        tvDate.text=item.update_time
        tvTableName.text=item.table
        tvWaiterName.text=item.waiter
        tvRoomName.text=item.order_id

        cardViewContinue.setOnClickListener {
            if (edComment.text.toString().length < 3){
                activity?.showWarning("Iltimos bekor qilish sababini iszoh maydoniga kiriting!")
            }else{
                viewModel.orderFoodCancel(item.id,edComment.text.toString())
            }
        }

    }
}