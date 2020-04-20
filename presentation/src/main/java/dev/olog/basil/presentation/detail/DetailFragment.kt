package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.math.MathUtils.clamp
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.main.RecipesViewModel
import dev.olog.basil.presentation.widget.sliding.sheet.SlidingSheet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_pager.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.abs

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<RecipesViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeCurrentRecipe()
            .onEach { updateData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onResume() {
        super.onResume()
        requireActivity().slidingSheet.addListener(listener)
        requireActivity().pager.registerOnPageChangeCallback(pagerCallback)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().slidingSheet.removeListener(listener)
        requireActivity().pager.unregisterOnPageChangeCallback(pagerCallback)
    }

    private fun updateData(recipe: Recipe?) {
        title.isVisible = recipe != null
        arrow.isVisible = recipe != null
        recipe ?: return

        description.text = recipe.description.take(recipe.description.indexOf(".") + 1)
        glutenFree.isVisible = recipe.allergens.glutenFree
        eggFree.isVisible = recipe.allergens.dairyFree
    }

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {

        private var lastPage = 0

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (position != lastPage) {
                // left
                title.translationX = title.width.toFloat() * (1 - positionOffset)
            } else {
                // right
                title.translationX = -title.width.toFloat() * positionOffset
            }
            println(title.translationX)

        }

        override fun onPageSelected(position: Int) {
            lastPage = position
        }

    }

    private val listener = object : SlidingSheet.Callback {

        private val contentAlphaThreshold = 0.6f
        private val titleAlphaAcceleration = 10
        private val contentAlphaAcceleration = 4

        override fun onOffsetChanged(view: SlidingSheet, offset: Float) {
            clickTheft.isClickable = offset < 0f
            clickTheft.isFocusable = clickTheft.isClickable

            if (offset > 0) {
                return
            }
            arrow.alpha = clamp(1 - abs(offset) * titleAlphaAcceleration, 0f, 1f)

            if (abs(offset) < contentAlphaThreshold) {
                fadeItems.alpha = 0f
                return
            }

            fadeItems.alpha = clamp(
                (abs(offset) - contentAlphaThreshold) * contentAlphaAcceleration,
                0f,
                1f
            )
        }

    }

}