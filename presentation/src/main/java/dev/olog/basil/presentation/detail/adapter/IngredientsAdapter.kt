package dev.olog.basil.presentation.detail.adapter

import dev.olog.basil.presentation.base.BaseAdapter
import dev.olog.basil.presentation.base.DataBoundViewHolder
import dev.olog.basil.presentation.detail.DisplayableIngredients
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsAdapter : BaseAdapter<DisplayableIngredients>() {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {

    }

    override fun bind(holder: DataBoundViewHolder, item: DisplayableIngredients, position: Int) {
        holder.view.apply {
            ingredient.text = item.name
            quantity.text = "${item.quantity} tbps"
        }
    }

    override fun updateDataSet(newData: List<DisplayableIngredients>) {
        val data = newData.sortedBy { it.order }
        super.updateDataSet(data)
    }
}
