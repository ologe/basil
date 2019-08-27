package dev.olog.basil.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.topMostFragment(): Fragment? {
    val entries = this.backStackEntryCount
    if (entries > 0) {
        return this.findFragmentByTag(this.getBackStackEntryAt(entries - 1).name)
    }
    return null
}