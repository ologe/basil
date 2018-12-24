package dev.olog.basil.presentation.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dev.olog.basil.presentation.actions.SearchFragment;

public class VerticalPagerAdapter extends FragmentPagerAdapter {

    public VerticalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new SearchFragment();
        }
        return new MainFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
