package dev.olog.basil.presentation.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.math.MathUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseFragment;
import dev.olog.basil.presentation.model.DisplayableRecipe;
import dev.olog.basil.presentation.widget.ScrimImageView;

public class MainFragment extends BaseFragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @Inject ViewModelProvider.Factory viewModelFactory;

    private View header;
    private View arrow;
    private View recipeHeader;
    private RecyclerView list;
//    bottomsheet
    private TextView title;
    private TextView description;
    private TextView calories;
    private TextView people;

    private SlidingUpPanelLayout slidingPanel;
    private SlidingPanelListener panelListener;
    private OnScrollListener onScrollListener;

    private MainFragmentViewModel viewModel;
    private RecipesViewPagerAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        panelListener = new SlidingPanelListener();
        onScrollListener = new OnScrollListener();

        adapter = new RecipesViewPagerAdapter();
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(list);

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainFragmentViewModel.class);
        viewModel.observeRecipes().observe(getViewLifecycleOwner(), recipes -> adapter.updateDataSet(recipes));

        viewModel.observeCurrentRecipe()
                .observe(getViewLifecycleOwner(), this::updateCurrentRecipe);

        viewModel.updatePosition(0);
    }

    private void findViews(View view){
        header = view.findViewById(R.id.header);
        arrow = view.findViewById(R.id.arrow);
        list = view.findViewById(R.id.list);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        calories = view.findViewById(R.id.calories);
        people = view.findViewById(R.id.people);
        recipeHeader = view.findViewById(R.id.recipeHeader);
        slidingPanel = view.findViewById(R.id.slidingPanel);
    }

    private void updateCurrentRecipe(@Nullable DisplayableRecipe recipe){
        if (recipe != null){
            title.setText(recipe.getName());
            description.setText(recipe.getDescription());
            calories.setText(String.valueOf(recipe.getCalories()));
            people.setText(String.valueOf(recipe.getPeople()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        slidingPanel.addPanelSlideListener(panelListener);
        list.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        slidingPanel.removePanelSlideListener(panelListener);
        list.removeOnScrollListener(onScrollListener);
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                int visiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (visiblePosition != RecyclerView.NO_POSITION){
                    viewModel.updatePosition(visiblePosition);
                }
            }
        }
    }

    private class SlidingPanelListener extends SlidingUpPanelLayout.SimplePanelSlideListener {

        // a cause della status bar
        private int statusBarHeight = 0;
        private int[] location = new int[]{0, 0};

        public SlidingPanelListener() {
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        }

        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            float translationY = (float) (header.getHeight() * slideOffset * 1.5);
            header.setTranslationY(-translationY);
            arrow.setAlpha(MathUtils.clamp(1 - slideOffset * 3f, 0f, 1f));
            drawScrim();
        }

        private void drawScrim(){
            recipeHeader.getLocationOnScreen(location);

            int visibleChild = layoutManager.findFirstCompletelyVisibleItemPosition();
            RecyclerView.ViewHolder viewHolder = list.findViewHolderForLayoutPosition(visibleChild);
            if (viewHolder == null){
                return;
            }
            ConstraintLayout root = (ConstraintLayout) viewHolder.itemView;
            ScrimImageView image = (ScrimImageView) root.getChildAt(0);
            int bottom = image.getBottom();

            boolean drawScrim = (location[1] - bottom - statusBarHeight) < 0;

            image.setDrawScrim(drawScrim);

            if (drawScrim){
                image.setScrimTop(location[1] - statusBarHeight);
            }
        }

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_main;
    }
}
