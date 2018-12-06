package dev.olog.basil.presentation.main;

import android.os.Bundle;

import javax.inject.Inject;

import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseActivity;
import dev.olog.basil.presentation.navigator.Navigator;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends BaseActivity {

    @Inject Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerticalViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new VerticalPager(getSupportFragmentManager()));
        pager.setCurrentItem(1);
    }
}
