package dev.olog.basil.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import java.util.*

class BasilGroup(
    context: Context,
    attrs: AttributeSet
) : Group(context, attrs) {

    private val views = WeakHashMap<Int, View>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode) {
            alpha = 0f
        }
    }

    override fun setAlpha(alpha: Float) {
        if (this.alpha != alpha) {
            super.setAlpha(alpha)
            getViews().forEach { it.alpha = alpha }
        }
    }

    private fun getViews(): Collection<View> {
        if (parent !is ConstraintLayout) {
            return emptyList()
        }
        referencedIds.forEach {
            views.getOrPut(it) { (parent as ConstraintLayout).findViewById<View>(it) }
        }
        return views.values
    }

}