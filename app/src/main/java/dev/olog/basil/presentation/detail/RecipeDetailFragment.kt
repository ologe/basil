package dev.olog.basil.presentation.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.olog.basil.R
import dev.olog.basil.presentation.detail.adapter.RecipeDetailAdapter
import dev.olog.basil.presentation.widget.NoBackgroundBottomSheet
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*

class RecipeDetailFragment : BottomSheetDialogFragment() {

    companion object {
        fun show(activity: FragmentActivity) {
            RecipeDetailFragment().show(activity.supportFragmentManager, "RecipeDetailFragment")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return NoBackgroundBottomSheet(requireContext(), theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.pager.adapter = RecipeDetailAdapter(childFragmentManager)
        view.tabs.setupWithViewPager(view.pager)
    }


}
