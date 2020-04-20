package dev.olog.basil.presentation.base

import androidx.annotation.LayoutRes
import dagger.android.support.DaggerFragment

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : DaggerFragment(layoutRes) {



}
