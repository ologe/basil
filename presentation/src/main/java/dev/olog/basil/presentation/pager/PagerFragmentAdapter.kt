package dev.olog.basil.presentation.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.olog.basil.core.Recipe
import dev.olog.basil.presentation.recipe.RecipeFragment

class PagerFragmentAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val data = mutableListOf<Recipe>()

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment {
        return RecipeFragment.newInstance(data[position])
    }

    fun updateDataSet(data: List<Recipe>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

}