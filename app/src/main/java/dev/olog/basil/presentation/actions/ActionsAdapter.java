package dev.olog.basil.presentation.actions;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import dev.olog.basil.presentation.createnew.NewRecipeFragment;
import dev.olog.basil.presentation.search.SearchFragment;

public class ActionsAdapter extends FragmentPagerAdapter {

    public ActionsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new SearchFragment();
        }
        return new NewRecipeFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}

