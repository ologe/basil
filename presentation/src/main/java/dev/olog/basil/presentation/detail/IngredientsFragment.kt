package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.detail.adapter.IngredientsAdapter
import dev.olog.basil.presentation.main.RecipesViewModel
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class IngredientsFragment : BaseFragment(R.layout.fragment_ingredients) {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<RecipesViewModel> { factory }

    private val adapter by lazyFast { IngredientsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.list.adapter = adapter
        view.list.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeCurrentIngredients()
            .onEach {
                adapter.updateDataSet(it.mapIndexed { index, ingredient ->
                    DisplayableIngredients(index, R.layout.item_ingredient, ingredient.name, ingredient.quantity, ingredient.order)
                })
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}