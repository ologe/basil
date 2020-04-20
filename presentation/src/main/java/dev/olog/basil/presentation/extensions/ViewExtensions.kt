package dev.olog.basil.presentation.extensions

import android.content.Context
import android.view.View
import androidx.annotation.Px

fun Context.dipf(value: Int): Float = (value * resources.displayMetrics.density)
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun View.setHeight(@Px value: Int) {
    val params = layoutParams ?: return
    params.height = value
    layoutParams = params
}