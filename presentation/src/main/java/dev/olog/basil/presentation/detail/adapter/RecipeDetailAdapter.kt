package dev.olog.basil.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.olog.basil.presentation.detail.DirectionsFragment
import dev.olog.basil.presentation.detail.IngredientsFragment
import dev.olog.basil.shared.throwNotHandled

class RecipeDetailAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> IngredientsFragment()
        1 -> DirectionsFragment()
        else -> throwNotHandled("view pager position=$position")
    }
}