package dev.olog.basil.presentation.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(provideLayoutId(), container, false);
        onViewBound(view, savedInstanceState);
        return view;
    }

    protected abstract void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState);

    @LayoutRes
    protected abstract int provideLayoutId();

}
