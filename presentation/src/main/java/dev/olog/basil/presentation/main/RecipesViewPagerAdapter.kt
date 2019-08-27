package dev.olog.basil.presentation.main

import com.bumptech.glide.Glide
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseAdapter
import dev.olog.basil.presentation.base.DataBoundViewHolder
import dev.olog.basil.presentation.base.ImageModel
import kotlinx.android.synthetic.main.item_recipe_image.view.*

class RecipesViewPagerAdapter : BaseAdapter<ImageModel>() {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
    }

    override fun bind(holder: DataBoundViewHolder, item: ImageModel, position: Int) {
        Glide.with(holder.view.context)
            .load(R.drawable.salmon)
            .into(holder.view.image)
    }
}
