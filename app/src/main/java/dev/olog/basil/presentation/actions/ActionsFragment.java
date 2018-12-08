package dev.olog.basil.presentation.actions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dev.olog.basil.R;
import dev.olog.basil.presentation.widget.StoppableViewPager;

public class ActionsFragment extends Fragment {

    private StoppableViewPager pager;
    private View search;
    private View newRecipe;
    private ImageView icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pager = view.findViewById(R.id.pager);
        pager.setAdapter(new ActionsAdapter(getChildFragmentManager()));
        pager.setSwipeEnabled(false);

        search = view.findViewById(R.id.searchHeader);
        newRecipe = view.findViewById(R.id.newRecipeHeader);
        icon = view.findViewById(R.id.icon);
    }

    @Override
    public void onResume() {
        super.onResume();
        search.setOnClickListener(v -> {
            pager.setCurrentItem(0);
            search.setAlpha(1f);
            newRecipe.setAlpha(.5f);
            icon.setImageResource(R.drawable.wrapped_search);
        });
        newRecipe.setOnClickListener(v -> {
            pager.setCurrentItem(1);
            search.setAlpha(.5f);
            newRecipe.setAlpha(1f);
            icon.setImageResource(R.drawable.wrapped_add);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        search.setOnClickListener(null);
        newRecipe.setOnClickListener(null);
    }
}
