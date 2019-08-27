package dev.olog.basil.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.olog.basil.presentation.RecipeTypeChooserFragment
import dev.olog.basil.shared.throwNotHandled

class VerticalPagerAdapter(
        activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RecipeTypeChooserFragment()
        1 -> MainFragment()
        else -> throwNotHandled("view pager position=$position")
    }
}
