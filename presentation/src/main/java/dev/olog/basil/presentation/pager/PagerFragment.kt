package dev.olog.basil.presentation.pager

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.main.RecipesViewModel
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pager.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PagerFragment : BaseFragment(R.layout.fragment_pager) {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<RecipesViewModel> { factory }
    private val adapter by lazyFast { PagerFragmentAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pager.adapter = adapter
        pager.offscreenPageLimit = 3

        viewModel.observeRecipes()
            .onEach { recipes ->
                // TODO check
                requireActivity().slidingSheet.isDraggable = recipes.isNotEmpty()

                adapter.updateDataSet(recipes)
                emptyState.isVisible = recipes.isEmpty()
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        pager.registerOnPageChangeCallback(callback)

    }

    override fun onPause() {
        super.onPause()
        pager.unregisterOnPageChangeCallback(callback)
    }

    private val callback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.updatePosition(position)
        }
    }

}
