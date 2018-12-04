package dev.olog.basil.presentation.detail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseFragment;

public class DetailFragment extends BaseFragment {

    public static String TAG = DetailFragment.class.getSimpleName();

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_detail;
    }
}
