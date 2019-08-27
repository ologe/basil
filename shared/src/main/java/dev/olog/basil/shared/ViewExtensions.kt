package dev.olog.basil.shared

import android.view.View

fun View.toggleVisibility(visible: Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}