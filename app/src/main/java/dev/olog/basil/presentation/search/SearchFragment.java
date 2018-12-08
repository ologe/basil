package dev.olog.basil.presentation.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseFragment;
import dev.olog.basil.presentation.main.MainFragmentViewModel;

public class SearchFragment extends BaseFragment {

    @Inject ViewModelProvider.Factory viewModelFactory;
    private MainFragmentViewModel viewModel;

    private EditText editText;
    private View send;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editText = view.findViewById(R.id.editText);
        send = view.findViewById(R.id.send);
        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        editText.setOnKeyListener(onKeyListener);
        send.setOnClickListener(v -> updateFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        editText.setOnKeyListener(null);
        send.setOnClickListener(null);
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
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_search;
    }
}
