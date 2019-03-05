package dev.olog.basil.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dev.olog.basil.presentation.detail.DirectionsFragment
import dev.olog.basil.presentation.detail.IngredientsFragment

class RecipeDetailAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return IngredientsFragment()
        }
        return DirectionsFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        if (position == 0) {
            return "Ingredients"
        }
        return "Directions"
    }
}