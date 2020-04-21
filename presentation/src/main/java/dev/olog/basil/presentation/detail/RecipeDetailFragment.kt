package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Gravity
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.tabs.TabLayoutMediator
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.detail.adapter.RecipeDetailAdapter
import dev.olog.basil.shared.lazyFast
import dev.olog.basil.shared.throwNotHandled
import kotlinx.android.synthetic.main.fragment_recipe_detail.*

// TODO not sure why sliding sheet is stealing touches
class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {

    companion object {
        private const val TAG = "RecipeDetailFragment"
        private const val INITIAL_PAGE = "initial.page"
        private const val RECIPE = "recipe"

        fun show(
            activity: FragmentActivity,
            initialPage: Int,
            recipe: Recipe
        ) {
            val fragment = RecipeDetailFragment().apply {
                arguments = bundleOf(
                    INITIAL_PAGE to initialPage,
                    RECIPE to recipe
                )
            }
            fragment.enterTransition = TransitionSet().apply {
                addTransition(ScaleTransition())
                addTransition(Slide(Gravity.BOTTOM))
            }
            activity.supportFragmentManager.commit {
                add(android.R.id.content, fragment, TAG)
                addToBackStack(TAG)
            }
        }
    }

    private val recipe by lazyFast {
        requireArguments().getParcelable<Recipe>(RECIPE)!!
    }
    private val tabMediator by lazyFast {
        TabLayoutMediator(tabs, pager, tabMediatorCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pager.adapter = RecipeDetailAdapter(this, recipe)
        pager.currentItem = requireArguments().getInt(INITIAL_PAGE, 0)
        pager.offscreenPageLimit = 2
    }

    override fun onResume() {
        super.onResume()
        tabMediator.attach()
    }

    override fun onPause() {
        super.onPause()
        tabMediator.detach()
    }

    private val tabMediatorCallback = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
        tab.text = when (position) {
            0 -> "Ingredients"
            1 -> "Directions"
            else -> throwNotHandled("tab position=$position")
        }
    }

}
