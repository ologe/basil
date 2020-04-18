package dev.olog.basil.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import dev.olog.basil.core.RecipeCategory
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.main.MainFragmentViewModel
import dev.olog.basil.presentation.utils.activityViewModelProvider
import dev.olog.basil.presentation.utils.subscribe
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipe_type_chooser.*
import kotlinx.android.synthetic.main.fragment_recipe_type_chooser.view.*
import javax.inject.Inject

class RecipeTypeChooserFragment : BaseFragment() {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazyFast { activityViewModelProvider<MainFragmentViewModel>(factory) }

    private var categories = mutableMapOf<RecipeCategory, TextView>()
    private var categoriesUnderline = mutableMapOf<RecipeCategory, View>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categories.clear()
        categoriesUnderline.clear()
        categories[RecipeCategory.Appetizer] = view.appetizersHeader
        categories[RecipeCategory.Entree] = view.entreesHeader
        categories[RecipeCategory.Dessert] = view.dessertsHeader
        categories[RecipeCategory.Cocktail] = view.cocktailsHeader

        categoriesUnderline[RecipeCategory.Appetizer] = view.appetizersLine
        categoriesUnderline[RecipeCategory.Entree] = view.entreesLine
        categoriesUnderline[RecipeCategory.Dessert] = view.dessertsLine
        categoriesUnderline[RecipeCategory.Cocktail] = view.cocktailsLine

        viewModel.observeCurrentRecipeCategory()
            .subscribe(viewLifecycleOwner) {
                updateVisibleHeader(it)
            }
    }

    override fun onResume() {
        super.onResume()
        appetizersHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Appetizer) }
        entreesHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Entree) }
        dessertsHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Dessert) }
        cocktailsHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Cocktail) }
    }

    override fun onPause() {
        super.onPause()
        appetizersHeader.setOnClickListener(null)
        entreesHeader.setOnClickListener(null)
        dessertsHeader.setOnClickListener(null)
        cocktailsHeader.setOnClickListener(null)
    }

    private fun updateVisibleHeader(category: RecipeCategory) {
        // workaround to remove bold and keep textview style
        val baseTypeFace = categories.entries.first { it.key == category }.value.typeface

        for ((key, value) in categories.entries) {
            value.setTypeface(baseTypeFace, if (key == category) Typeface.BOLD else Typeface.NORMAL)
            categoriesUnderline[key]?.isVisible = key == category
        }
        // scroll to recipes
        requireActivity().pager.currentItem = 1
    }

    override fun provideLayoutId(): Int = R.layout.fragment_recipe_type_chooser
}
