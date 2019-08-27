package dev.olog.basil.presentation.main

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.math.MathUtils
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.HIDDEN
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.base.ImageModel
import dev.olog.basil.presentation.detail.RecipeDetailFragment
import dev.olog.basil.presentation.utils.activityViewModelProvider
import dev.olog.basil.presentation.utils.subscribe
import dev.olog.basil.presentation.widget.ParallaxScrimImageView
import dev.olog.basil.shared.lazyFast
import dev.olog.basil.shared.toggleVisibility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_recipe_image.view.*
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val panelListener by lazyFast { SlidingPanelListener() }
    private val onScrollListener by lazyFast { OnScrollListener() }

    private val viewModel by lazyFast {
        activityViewModelProvider<MainFragmentViewModel>(factory)
    }
    private val recipeImagesAdapter by lazyFast { RecipesViewPagerAdapter() }
    private val recipeImageLayoutManager by lazy {
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private val recipeTitlesAdapter by lazyFast { RecipeTitleAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleOnlyList.adapter = recipeTitlesAdapter
        titleOnlyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        titleOnlyList.requestDisallowInterceptTouchEvent(true)

        imageOnlyList.layoutManager = recipeImageLayoutManager
        imageOnlyList.adapter = recipeImagesAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.imageOnlyList)

        viewModel.updatePosition(0)

        viewModel.observeRecipes()
            .subscribe(viewLifecycleOwner) { recipes ->
                recipeImagesAdapter.updateDataSet(recipes.mapIndexed { index, recipe -> ImageModel(index, R.layout.item_recipe_image, recipe.image) })
                recipeTitlesAdapter.updateDataSet(recipes.mapIndexed { index, recipe -> TitleModel(index, R.layout.item_recipe_title, recipe.name) })
                adjustDetailBorders()
                emptyState.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
            }

        viewModel.observeCurrentRecipe()
            .subscribe(viewLifecycleOwner) {
                updateCurrentRecipe(it)
            }
    }

    override fun onResume() {
        super.onResume()
        slidingPanel.addPanelSlideListener(panelListener)
        imageOnlyList.addOnScrollListener(onScrollListener)
        bottomWrapper.setOnClickListener { RecipeDetailFragment.show(requireActivity()) }
    }

    override fun onPause() {
        super.onPause()
        slidingPanel.removePanelSlideListener(panelListener)
        imageOnlyList.removeOnScrollListener(onScrollListener)
        bottomWrapper.setOnClickListener(null)
    }

    private fun updateCurrentRecipe(recipe: Recipe?) {
        if (recipe != null) {
            description.text = recipe.description
            glutenFree.toggleVisibility(recipe.allergens.glutenFree)
            eggFree.toggleVisibility(recipe.allergens.dairyFree)
        }
    }

    private fun adjustDetailBorders() {
        imageOnlyList.setOnApplyWindowInsetsListener { v, insets ->
            val firstVisible = recipeImageLayoutManager.findFirstCompletelyVisibleItemPosition()
            val viewHolder = imageOnlyList.findViewHolderForLayoutPosition(firstVisible)
            viewHolder?.let {
                val imageView = it.itemView.image
                val marginHorizontal = imageView.left
                val location = intArrayOf(0, 0)
                imageView.getLocationOnScreen(location)

                val layoutParams = scrim.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.marginStart = marginHorizontal
                layoutParams.marginEnd = marginHorizontal
                layoutParams.topMargin = location[1] - insets.systemWindowInsetTop
                scrim.layoutParams = layoutParams

                insets
            }
        }
    }

    private inner class OnScrollListener : RecyclerView.OnScrollListener() {

        // update detail fragment data
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == SCROLL_STATE_IDLE) {
                val visiblePosition = recipeImageLayoutManager.findFirstCompletelyVisibleItemPosition()
                if (visiblePosition != NO_POSITION) {
                    viewModel.updatePosition(visiblePosition)
                }
            }
        }

        // sync scroll
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            titleOnlyList.scrollBy(dx, dy)
        }
    }

    private inner class SlidingPanelListener : SlidingUpPanelLayout.SimplePanelSlideListener() {

        // a cause della status bar
        private var statusBarHeight = 0
        private val location = intArrayOf(0, 0)

        init {
            headerWrapper.setOnApplyWindowInsetsListener { v, insets ->
                statusBarHeight = insets.systemWindowInsetTop
                insets
            }
        }

        override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
            toggleViewPagerTouch(newState == COLLAPSED || newState == HIDDEN)
        }

        private fun toggleViewPagerTouch(enabled: Boolean) {
            requireActivity().pager.isUserInputEnabled = enabled
        }

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            val translationY = (headerWrapper.height.toDouble() * slideOffset.toDouble() * 1.5).toFloat()
            headerWrapper.translationY = -translationY
            arrow.alpha = MathUtils.clamp(1 - slideOffset * 3f, 0f, 1f)

            divider.alpha = slideOffset
            bottomWrapper.alpha = slideOffset
            description.alpha = slideOffset

            drawScrim()
        }

        private fun drawScrim() {
            recipeHeader.getLocationOnScreen(location)

            val visibleChild = recipeImageLayoutManager.findFirstCompletelyVisibleItemPosition()
            val viewHolder = imageOnlyList.findViewHolderForLayoutPosition(visibleChild) ?: return
            val root = viewHolder.itemView as ConstraintLayout
            val image = root.getChildAt(0) as ParallaxScrimImageView
            val bottom = image.bottom

            val drawScrim = location[1] - bottom - statusBarHeight < 0

            image.setDrawScrim(drawScrim)

            if (drawScrim) {
                image.setScrimTop(location[1] - statusBarHeight)
            }
        }

    }

    override fun provideLayoutId(): Int = R.layout.fragment_main
}
