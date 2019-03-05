package dev.olog.basil.presentation.widget

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.FrameLayout

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.olog.basil.R

class NoBackgroundBottomSheet : BottomSheetDialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, cancelListener) {}

    override fun setContentView(view: View) {
        super.setContentView(view)
        window!!.findViewById<View>(R.id.design_bottom_sheet).background = null
        val bottomSheet = window.decorView.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val mBehavior = BottomSheetBehavior.from(bottomSheet)
        mBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
