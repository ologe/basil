package dev.olog.basil.presentation.main

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.HIDDEN
import dev.olog.basil.R
import dev.olog.basil.model.Recipe
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.detail.RecipeDetailFragment
import dev.olog.basil.presentation.widget.ParallaxScrimImageView
import dev.olog.basil.presentation.widget.StoppableVerticalViewPager
import dev.olog.basil.utils.doOnPreDraw
import dev.olog.basil.utils.statusBarHeight
import dev.olog.basil.utils.toggleVisibility
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_recipe_image.view.*

class MainFragment : BaseFragment() {

    private val panelListener by lazy { SlidingPanelListener() }
    private val onScrollListener by lazy { OnScrollListener() }

    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }
    private val recipeImagesAdapter by lazy { RecipesViewPagerAdapter() }
    private val recipeImageLayoutManager by lazy {
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private val recipeTitlesAdapter by lazy { RecipeTitleAdapter() }

    private val disposables = CompositeDisposable()

    override fun onViewBound(view: View, savedInstanceState: Bundle?) {

        view.titleOnlyList.adapter = recipeTitlesAdapter
        view.titleOnlyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        view.titleOnlyList.requestDisallowInterceptTouchEvent(true)

        view.imageOnlyList.layoutManager = recipeImageLayoutManager
        view.imageOnlyList.adapter = recipeImagesAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.imageOnlyList)
    }

    override fun onStart() {
        super.onStart()
        viewModel.observeRecipes().subscribe { recipes ->
            recipeImagesAdapter.updateDataSet(recipes.map { it.image })
            recipeTitlesAdapter.updateDataSet(recipes.map { it.name })
            adjustDetailBorders()
            emptyState.visibility = if (recipes.isEmpty()) View.VISIBLE else View.GONE
        }.addTo(disposables)

        viewModel.observeCurrentRecipe()
                .subscribe { this.updateCurrentRecipe(it) }
                .addTo(disposables)

        viewModel.updatePosition(0)

        bottomWrapper.setOnClickListener { RecipeDetailFragment.show(requireActivity()) }
    }

    override fun onResume() {
        super.onResume()
        slidingPanel.addPanelSlideListener(panelListener)
        imageOnlyList.addOnScrollListener(onScrollListener)
    }

    override fun onPause() {
        super.onPause()
        slidingPanel.removePanelSlideListener(panelListener)
        imageOnlyList.removeOnScrollListener(onScrollListener)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
        bottomWrapper.setOnClickListener(null)
    }

    private fun updateCurrentRecipe(recipe: Recipe?) {
        if (recipe != null) {
            description.text = recipe.description
            calories.text = "${recipe.macros.calories}g"
            protein.text = "${recipe.macros.proteins}g"
            fat.text = "${recipe.macros.fat}g"
            glutenFree.toggleVisibility(recipe.allergens.glutenFree)
            eggFree.toggleVisibility(recipe.allergens.eggFree)
        }
    }

    private fun adjustDetailBorders() {
        imageOnlyList.doOnPreDraw {
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
                layoutParams.topMargin = location[1] - resources.statusBarHeight()
                scrim.layoutParams = layoutParams
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

    private fun toggleViewPagerTouch(enabled: Boolean) {
        val pager = requireActivity().findViewById<StoppableVerticalViewPager>(R.id.pager)
        pager.setSwipeEnabled(enabled)
    }

    private inner class SlidingPanelListener : SlidingUpPanelLayout.SimplePanelSlideListener() {

        // a cause della status bar
        private var statusBarHeight = 0
        private val location = intArrayOf(0, 0)

        init {
            statusBarHeight = resources.statusBarHeight()
        }

        override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
            toggleViewPagerTouch(newState == COLLAPSED || newState == HIDDEN)
        }

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            val translationY = (headerWrapper.height.toDouble() * slideOffset.toDouble() * 1.5).toFloat()
            headerWrapper.translationY = -translationY
            arrow.alpha = MathUtils.clamp(1 - slideOffset * 3f, 0f, 1f)

            divider.alpha = slideOffset
            midWrapper.alpha = slideOffset
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
