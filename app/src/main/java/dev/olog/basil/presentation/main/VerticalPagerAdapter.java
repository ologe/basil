package dev.olog.basil.presentation.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dev.olog.basil.presentation.actions.ActionsFragment;
import dev.olog.basil.presentation.search.SearchFragment;

public class VerticalPagerAdapter extends FragmentPagerAdapter {

    public VerticalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new ActionsFragment();
        }
        return new MainFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
