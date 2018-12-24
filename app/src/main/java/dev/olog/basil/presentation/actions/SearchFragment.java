package dev.olog.basil.presentation.actions;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseFragment;
import dev.olog.basil.presentation.main.MainFragmentViewModel;
import dev.olog.basil.presentation.navigator.Navigator;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class SearchFragment extends BaseFragment {

    private EditText editText;
    private View send;
    private View newRecipe;

    @Inject Navigator navigator;

    @Inject ViewModelProvider.Factory viewModelFactory;
    private MainFragmentViewModel viewModel;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainFragmentViewModel.class);
        editText = view.findViewById(R.id.editText);
        send = view.findViewById(R.id.send);
        newRecipe = view.findViewById(R.id.newRecipeHeader);
    }

    @Override
    public void onResume() {
        super.onResume();
        editText.setOnKeyListener(onKeyListener);
        send.setOnClickListener(v -> updateFilter());
        newRecipe.setOnClickListener(v -> navigator.newRecipe());
    }

    @Override
    public void onPause() {
        super.onPause();
        editText.setOnKeyListener(null);
        send.setOnClickListener(null);
        newRecipe.setOnClickListener(null);
    }

    private View.OnKeyListener onKeyListener = (v, keyCode, event) -> {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            updateFilter();
            return true;
        }
        return false;
    };

    private void updateFilter(){
        viewModel.updateFilter(editText.getText().toString());
        ((VerticalViewPager) requireActivity().findViewById(R.id.pager))
                .setCurrentItem(1);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_search;
    }
}
