package dev.olog.basil.presentation.search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseFragment;

public class SearchFragment extends BaseFragment {

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_search;
    }
}
