package dev.olog.basil.presentation.recipe

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.widget.sliding.sheet.SlidingSheet
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlin.math.abs

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    companion object {

        private const val RECIPE = "recipe"

        fun newInstance(recipe: Recipe): RecipeFragment {
            return RecipeFragment().apply {
                arguments = bundleOf(RECIPE to recipe)
            }
        }

    }

    private val recipe by lazyFast {
        requireArguments().getParcelable<Recipe>(RECIPE)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(requireContext())
            .load(recipe.image)
            .into(image)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().slidingSheet.addListener(listener)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().slidingSheet.removeListener(listener)
    }

    private val listener = object : SlidingSheet.Callback {

        private val scrimAcceleration = 2f

        override fun onOffsetChanged(view: SlidingSheet, offset: Float) {
            if (offset > 0) {
                image.scrimPercentage = 0f
                return
            }
            image.scrimPercentage = abs(offset * scrimAcceleration)
        }
    }

}