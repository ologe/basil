package dev.olog.basil.presentation.detail.adapter

import androidx.databinding.ViewDataBinding
import dev.olog.basil.BR
import dev.olog.basil.R
import dev.olog.basil.model.Ingredient
import dev.olog.basil.presentation.base.BaseAdapter

class IngredientsAdapter : BaseAdapter<Ingredient>() {

    override fun initViewHolderListeners(binding: ViewDataBinding) {

    }

    override fun bind(binding: ViewDataBinding, item: Ingredient, position: Int) {
        binding.setVariable(BR.item, item)
    }

    override fun provideLayoutId(): Int {
        return R.layout.item_ingredient
    }


    override fun updateDataSet(newData: List<Ingredient>) {
        val data = newData.sortedBy { it.order }
        super.updateDataSet(data)
    }
}
