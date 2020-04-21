package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.shared.lazyFast

class DirectionsFragment : BaseFragment(R.layout.fragment_directions) {

    companion object {
        private const val RECIPE = "recipe"

        fun newInstance(recipe: Recipe): DirectionsFragment {
            return DirectionsFragment().apply {
                arguments = bundleOf(RECIPE to recipe)
            }
        }
    }

    private val recipe by lazyFast {
        requireArguments().getParcelable<Recipe>(RECIPE)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}