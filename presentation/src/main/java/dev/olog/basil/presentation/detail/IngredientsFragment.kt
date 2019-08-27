package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.detail.adapter.IngredientsAdapter
import dev.olog.basil.presentation.main.MainFragmentViewModel
import dev.olog.basil.presentation.utils.activityViewModelProvider
import dev.olog.basil.presentation.utils.subscribe
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import javax.inject.Inject

class IngredientsFragment : BaseFragment() {

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazyFast { activityViewModelProvider<MainFragmentViewModel>(factory) }

    private val adapter by lazyFast { IngredientsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.list.adapter = adapter
        view.list.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeCurrentIngredients()
            .subscribe(viewLifecycleOwner) {
                adapter.updateDataSet(it.mapIndexed { index, ingredient ->
                    DisplayableIngredients(index, R.layout.item_ingredient, ingredient.name, ingredient.quantity, ingredient.order)
                })
            }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_ingredients
}