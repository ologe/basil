package dev.olog.basil.utils

import android.content.res.Resources

fun Resources.statusBarHeight(): Int {
    val resourceId = this.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        this.getDimensionPixelSize(resourceId)
    } else 0
}
