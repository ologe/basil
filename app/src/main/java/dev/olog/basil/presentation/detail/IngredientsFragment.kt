package dev.olog.basil.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dev.olog.basil.R
import dev.olog.basil.presentation.base.BaseFragment
import dev.olog.basil.presentation.detail.adapter.IngredientsAdapter
import dev.olog.basil.presentation.main.MainActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : BaseFragment() {

    private val adapter by lazy { IngredientsAdapter() }
    private val viewModel by lazy { (requireActivity() as MainActivity).viewModel }
    private var disposable: Disposable? = null

    override fun onViewBound(view: View, savedInstanceState: Bundle?) {
        view.list.adapter = adapter
        view.list.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()
        disposable = viewModel.observeCurrentIngredients()
                .subscribe { adapter.updateDataSet(it) }

    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    override fun provideLayoutId(): Int = R.layout.fragment_ingredients
}