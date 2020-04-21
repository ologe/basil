package dev.olog.basil.presentation.drawer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.extensions.dip
import dev.olog.basil.presentation.main.RecipesViewModel
import dev.olog.basil.presentation.widget.sliding.sheet.SlidingSheet
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_drawer.*
import javax.inject.Inject

class DrawerFragment : BaseFragment(R.layout.fragment_drawer) {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<RecipesViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        drawer.listener = {
//            viewModel.updateVisibleCategory(it) TODO scroll dies
        }
        requireActivity().slidingSheet.addListener(listener)
    }

    override fun onPause() {
        super.onPause()
        drawer.listener = null
        requireActivity().slidingSheet.removeListener(listener)
    }

    private val listener = object : SlidingSheet.Callback {

        private var translate = true

        private val acceleration by lazyFast {
            requireContext().dip(128)
        }

        override fun onOffsetChanged(view: SlidingSheet, offset: Float) {
            if (offset == 0f) {
                translate = true
            } else if (offset == 1f){
                translate = false
            }

            if (offset < 0 || !translate) {
                return
            }
            val invertedOffset = 1 - offset
            val minTranslation = -invertedOffset * acceleration
            val maxTranslation = drawer.computeTranslationY(minTranslation, drawer.childCount)
            drawer.translationY = minTranslation
            divider.translationY = maxTranslation
            shoppingListHeader.translationY = maxTranslation
            icon.translationY = maxTranslation
        }

    }

}
