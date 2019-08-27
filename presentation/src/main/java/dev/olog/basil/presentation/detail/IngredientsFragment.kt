package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.detail.adapter.IngredientsAdapter
import dev.olog.basil.presentation.main.MainActivity
import dev.olog.basil.shared.lazyFast
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class IngredientsFragment : BaseFragment() {

    private val adapter by lazyFast { IngredientsAdapter() }
    private val viewModel by lazyFast { (requireActivity() as MainActivity).viewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.list.adapter = adapter
        view.list.layoutManager = LinearLayoutManager(requireContext())

        viewModel.observeCurrentIngredients()
            .onEach { adapter.updateDataSet(it.mapIndexed { index, ingredient ->
                DisplayableIngredients(index, R.layout.item_ingredient, ingredient.name, ingredient.quantity, ingredient.order)
            }) }
            .catch { it.printStackTrace() }
            .launchIn(this)
    }

    override fun provideLayoutId(): Int = R.layout.fragment_ingredients
}