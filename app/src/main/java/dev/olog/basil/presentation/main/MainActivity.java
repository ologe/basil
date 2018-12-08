package dev.olog.basil.presentation.main;

import android.os.Bundle;
import android.view.View;

import com.github.dmstocking.optional.java.util.Optional;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseActivity;
import dev.olog.basil.presentation.navigator.Navigator;
import dev.olog.basil.presentation.widget.StoppableVerticalViewPager;
import dev.olog.basil.utils.ListUtils;

public class MainActivity extends BaseActivity {

    @Inject Navigator navigator;

    private StoppableVerticalViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        pager.setAdapter(new VerticalPagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0){
            pager.setCurrentItem(1);
            return;
        } else if (pager.getCurrentItem() == 1){
            SlidingUpPanelLayout slidingPanel = findSlidingPanel();
            if (slidingPanel != null){
                if (slidingPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
                    slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return;
                }
            }
        }

        super.onBackPressed();
    }

    @Nullable
    private SlidingUpPanelLayout findSlidingPanel(){
        Optional<Fragment> fragment = ListUtils.find(getSupportFragmentManager().getFragments(), f -> f instanceof MainFragment);
        if (fragment.isPresent()){
            View view = fragment.get().getView();
            if (view != null){
                return view.findViewById(R.id.slidingPanel);
            }
        }
        return null;
    }

}
