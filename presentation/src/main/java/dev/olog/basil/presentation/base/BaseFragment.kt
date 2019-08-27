package dev.olog.basil.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseFragment : Fragment(), CoroutineScope by MainScope() {

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(provideLayoutId(), container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancel()
    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

}
