package dev.olog.basil.utils

import android.view.View
import android.view.ViewTreeObserver

fun View.doOnPreDraw(action: () -> Unit) {
    this.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            this@doOnPreDraw.viewTreeObserver.removeOnPreDrawListener(this)
            action()
            return false
        }
    })
}

fun View.toggleVisibility(visible: Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}