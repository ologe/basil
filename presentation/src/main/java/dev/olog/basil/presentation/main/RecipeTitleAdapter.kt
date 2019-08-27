package dev.olog.basil.presentation.main

import dev.olog.basil.presentation.base.BaseAdapter
import dev.olog.basil.presentation.base.DataBoundViewHolder
import kotlinx.android.synthetic.main.item_recipe_title.view.*

class RecipeTitleAdapter : BaseAdapter<TitleModel>() {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {

    }

    override fun bind(holder: DataBoundViewHolder, item: TitleModel, position: Int) {
        holder.view.apply {
            title.text = item.title
        }
    }
}
