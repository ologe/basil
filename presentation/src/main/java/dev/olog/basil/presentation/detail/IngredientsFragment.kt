package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsFragment : BaseFragment(R.layout.fragment_ingredients) {

    companion object {
        private const val RECIPE = "recipe"

        fun newInstance(recipe: Recipe): IngredientsFragment {
            return IngredientsFragment().apply {
                arguments = bundleOf(RECIPE to recipe)
            }
        }
    }

    private val recipe by lazyFast {
        requireArguments().getParcelable<Recipe>(RECIPE)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        for (ingredient in recipe.ingredients) {
            val itemView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_ingredient, ingredients, false)
            itemView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemView.ingredient.text = ingredient.name.capitalize()
            itemView.quantity.text = "${ingredient.quantity} tbps"
            ingredients.addView(itemView)
        }

    }

}