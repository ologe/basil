package dev.olog.basil.presentation.main

import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import dev.olog.basil.R
import dev.olog.basil.presentation.base.BaseAdapter
import kotlinx.android.synthetic.main.item_recipe_image.view.*

class RecipesViewPagerAdapter : BaseAdapter<Int>() {

    override fun initViewHolderListeners(binding: ViewDataBinding) {

    }

    override fun bind(binding: ViewDataBinding, item: Int, position: Int) {
        Glide.with(binding.root)
                .load(item)
                .into(binding.root.image)
    }

    override fun provideLayoutId(): Int {
        return R.layout.item_recipe_image
    }
}
