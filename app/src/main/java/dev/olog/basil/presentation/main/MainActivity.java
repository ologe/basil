package dev.olog.basil.presentation.main;

import android.os.Bundle;

import javax.inject.Inject;

import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseActivity;
import dev.olog.basil.presentation.navigator.Navigator;

public class MainActivity extends BaseActivity {

    @Inject Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            navigator.toMainFragment();
        }
    }
}
