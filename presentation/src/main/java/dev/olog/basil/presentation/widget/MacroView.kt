package dev.olog.basil.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import dev.olog.basil.presentation.R
import kotlinx.android.synthetic.main.item_macros.view.*

class MacroView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    init {
        gravity = Gravity.CENTER
        orientation = VERTICAL

        View.inflate(context, R.layout.item_macros, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.MacroView)

        text.text = a.getString(R.styleable.MacroView_macro_name) ?: "not set"
        amount.text = "${a.getInt(R.styleable.MacroView_macro_amount, 0)}g" ?: "not set"

        a.recycle()
    }


}