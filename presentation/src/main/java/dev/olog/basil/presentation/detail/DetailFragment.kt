package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.math.MathUtils.clamp
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.transition.MaterialSharedAxis
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.main.RecipesViewModel
import dev.olog.basil.presentation.widget.sliding.sheet.SlidingSheet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_pager.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.abs

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<RecipesViewModel> { factory }

    private var currentRecipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, false)
        reenterTransition = MaterialSharedAxis.create(MaterialSharedAxis.Z, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeRecipes()
            .map { it.map { it.name } }
            .onEach { textSwitcher.updateTexts(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.observeCurrentRecipe()
            .onEach { updateData(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }

    override fun onResume() {
        super.onResume()
        requireActivity().slidingSheet.addListener(listener)
        requireActivity().pager.registerOnPageChangeCallback(pagerCallback)
        requireActivity().ingredients.setOnClickListener {
            RecipeDetailFragment.show(requireActivity(), 0, currentRecipe!!)
        }
        requireActivity().directions.setOnClickListener {
            RecipeDetailFragment.show(requireActivity(), 1, currentRecipe!!)
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().slidingSheet.removeListener(listener)
        requireActivity().pager.unregisterOnPageChangeCallback(pagerCallback)
        requireActivity().ingredients.setOnClickListener(null)
        requireActivity().directions.setOnClickListener(null)
    }

    private fun updateData(recipe: Recipe?) {
        this.currentRecipe = recipe
        textSwitcher.isVisible = recipe != null
        arrow.isVisible = recipe != null
        recipe ?: return

        description.text = recipe.description.take(recipe.description.indexOf(".") + 1).parseAsHtml()
        glutenFree.isVisible = recipe.allergens.glutenFree
        eggFree.isVisible = recipe.allergens.dairyFree
    }

    private val pagerCallback = object : ViewPager2.OnPageChangeCallback() {

        private var lastPage = 0
        private var isSettling = false

        // fricking view pager callback
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (positionOffset == 0f) {
                textSwitcher.translateOffset(0f)
                return
            }
            if (position != lastPage) {
                // left
                if (isSettling) {
                    textSwitcher.translateOffset(positionOffset)
                } else {
                    textSwitcher.translateOffset(-(1 - positionOffset))
                }
            } else {
                // right
                if (isSettling) {
                    textSwitcher.translateOffset(-(1 - positionOffset))
                } else {
                    textSwitcher.translateOffset(positionOffset)
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            isSettling = state == ViewPager2.SCROLL_STATE_SETTLING
        }

        override fun onPageSelected(position: Int) {
            textSwitcher.updateCurrentPage(position)
            lastPage = position
        }

    }

    private val listener = object : SlidingSheet.Callback {

        private val contentAlphaThreshold = 0.6f
        private val titleAlphaAcceleration = 10
        private val contentAlphaAcceleration = 3

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