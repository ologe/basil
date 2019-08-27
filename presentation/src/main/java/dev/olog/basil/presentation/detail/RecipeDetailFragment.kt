package dev.olog.basil.presentation.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.detail.adapter.RecipeDetailAdapter
import dev.olog.basil.shared.exhaustive
import dev.olog.basil.shared.throwNotHandled
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*

class RecipeDetailFragment : BottomSheetDialogFragment() {

    companion object {
        fun show(activity: FragmentActivity) {
            RecipeDetailFragment().show(activity.supportFragmentManager, "RecipeDetailFragment")
        }
    }

    private val tabMediator by lazy { TabLayoutMediator(tabs, pager, tabMediatorCallback) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.pager.adapter = RecipeDetailAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        tabMediator.attach()
    }

    override fun onPause() {
        super.onPause()
        tabMediator.detach()
    }

    private val tabMediatorCallback = TabLayoutMediator.OnConfigureTabCallback { _, position ->
        when (position) {
            0 -> "Ingredients"
            1 -> "Directions"
            else -> throwNotHandled("tab position=$position")
        }.exhaustive
    }

}
