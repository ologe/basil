package dev.olog.basil.presentation.recipetype

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import dev.olog.basil.R
import dev.olog.basil.model.RecipeCategory
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.main.MainActivity
import dev.olog.basil.utils.toggleVisibility
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipe_type_chooser.*
import kotlinx.android.synthetic.main.fragment_recipe_type_chooser.view.*

class RecipeTypeChooserFragment : BaseFragment() {

    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }

    private var disposable: Disposable? = null

    private var categories = mutableMapOf<RecipeCategory, TextView>()
    private var categoriesUnderline = mutableMapOf<RecipeCategory, View>()

    override fun onViewBound(view: View, savedInstanceState: Bundle?) {
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

    }

    override fun onStart() {
        super.onStart()
        disposable = viewModel.observeCurrentRecipeCategory()
                .subscribe(this::updateVisibleHeader, Throwable::printStackTrace)

        appetizersHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Appetizer) }
        entreesHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Entree) }
        dessertsHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Dessert) }
        cocktailsHeader.setOnClickListener { viewModel.updateVisibleCategory(RecipeCategory.Cocktail) }
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
        appetizersHeader.setOnClickListener(null)
        entreesHeader.setOnClickListener(null)
        dessertsHeader.setOnClickListener(null)
        cocktailsHeader.setOnClickListener(null)
    }

    private fun updateVisibleHeader(category: RecipeCategory){
        // workaround to remove bold and keep textview style
        val baseTypeFace = categories.entries.first { it.key == category }.value.typeface

        for ((key, value) in categories.entries) {
            value.setTypeface(baseTypeFace, if (key == category) Typeface.BOLD else Typeface.NORMAL)
            categoriesUnderline[key]?.toggleVisibility(key == category)
        }
        // scroll to recipes
        requireActivity().pager.currentItem = 1
    }

    override fun provideLayoutId(): Int {
        return R.layout.fragment_recipe_type_chooser
    }
}
