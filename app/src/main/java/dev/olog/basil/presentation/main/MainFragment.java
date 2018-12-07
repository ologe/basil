package dev.olog.basil.presentation.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

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
import dev.olog.basil.domain.entity.Tag;
import dev.olog.basil.presentation.base.BaseFragment;
import dev.olog.basil.presentation.model.DisplayableMiniRecipe;
import dev.olog.basil.presentation.model.DisplayableRecipe;
import dev.olog.basil.presentation.navigator.Navigator;
import dev.olog.basil.presentation.widget.ParallaxScrimImageView;
import dev.olog.basil.presentation.widget.StoppableVerticalViewPager;
import dev.olog.basil.utils.ListUtils;
import dev.olog.basil.utils.WindowUtils;
import io.reactivex.Observable;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.HIDDEN;

public class MainFragment extends BaseFragment {

    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject Navigator navigator;

    private ChipGroup tagsGroup;
    private View headerWrapper;
    private View arrow;
    private View recipeHeader;
    private RecyclerView list;
//    bottomsheet
    private TextView description;
    private TextView calories;
    private TextView people;
    private View divider;
    private View midWrapper;
    private View bottomWrapper;
    private View descriptionWrapper;
    private View ingredients;
    private View scrim;

    private RecyclerView recipeTitleList;

    private SlidingUpPanelLayout slidingPanel;
    private SlidingPanelListener panelListener;
    private OnScrollListener onScrollListener;

    private MainFragmentViewModel viewModel;
    private RecipesViewPagerAdapter imageAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViews(view);
        panelListener = new SlidingPanelListener();
        onScrollListener = new OnScrollListener();

        imageAdapter = new RecipesViewPagerAdapter();
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        RecipeTitleAdapter recipeTitleAdapter = new RecipeTitleAdapter();
        recipeTitleList.setAdapter(recipeTitleAdapter);
        recipeTitleList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recipeTitleList.requestDisallowInterceptTouchEvent(true);

        list.setLayoutManager(layoutManager);
        list.setAdapter(imageAdapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(list);

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainFragmentViewModel.class);

        viewModel.observeRecipes().observe(getViewLifecycleOwner(), recipes -> {
            imageAdapter.updateDataSet(recipes);
            recipeTitleAdapter.updateDataSet(ListUtils.map(recipes, DisplayableMiniRecipe::getTitle));
            adjustDetailBorders();
        });

        viewModel.observeCurrentRecipe()
                .observe(getViewLifecycleOwner(), this::updateCurrentRecipe);

        viewModel.updatePosition(0);
    }

    private void findViews(View view){
        headerWrapper = view.findViewById(R.id.headerWrapper);
        arrow = view.findViewById(R.id.arrow);
        list = view.findViewById(R.id.list);
        description = view.findViewById(R.id.description);
        calories = view.findViewById(R.id.calories);
        people = view.findViewById(R.id.people);
        recipeHeader = view.findViewById(R.id.recipeHeader);
        slidingPanel = view.findViewById(R.id.slidingPanel);
        divider = view.findViewById(R.id.divider);
        midWrapper = view.findViewById(R.id.midWrapper);
        bottomWrapper = view.findViewById(R.id.bottomWrapper);
        descriptionWrapper = view.findViewById(R.id.descriptionWrapper);
        ingredients = view.findViewById(R.id.ingredients);
        tagsGroup = view.findViewById(R.id.tags);
        recipeTitleList = view.findViewById(R.id.titleList);
        scrim = view.findViewById(R.id.scrim);
    }

    private void updateCurrentRecipe(@Nullable DisplayableRecipe recipe){
        if (recipe != null){
            description.setText(recipe.getDescription());
            calories.setText(recipe.getCalories());
            people.setText(recipe.getPeople());

            tagsGroup.removeAllViews();
            List<String> tags = Observable.fromIterable(recipe.getTags())
                    .map(Tag::getValue)
                    .toList()
                    .blockingGet();
            for (String tag : tags) {
                Chip chip = new Chip(requireContext());
                chip.setText(tag);
                tagsGroup.addView(chip);
            }
        }
    }

    private void adjustDetailBorders(){
        list.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                list.getViewTreeObserver().removeOnPreDrawListener(this);
                int firstVisible = layoutManager.findFirstCompletelyVisibleItemPosition();
                RecyclerView.ViewHolder viewHolder = list.findViewHolderForLayoutPosition(firstVisible);
                if (viewHolder != null){
                    View imageView = viewHolder.itemView.findViewById(R.id.image);
                    int marginHorizontal = imageView.getLeft();
                    int[] location = new int[]{0, 0};
                    imageView.getLocationOnScreen(location);

                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) scrim.getLayoutParams();
                    layoutParams.setMarginStart(marginHorizontal);
                    layoutParams.setMarginEnd(marginHorizontal);
                    layoutParams.topMargin = location[1] - WindowUtils.getStatusBarHeight(getResources());
                    scrim.setLayoutParams(layoutParams);
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        slidingPanel.addPanelSlideListener(panelListener);
        list.addOnScrollListener(onScrollListener);
        ingredients.setOnClickListener(v -> navigator.toIngredientsFragment(viewModel.getCurrentId()));
        slidingPanel.setScrollableView(descriptionWrapper);
    }

    @Override
    public void onPause() {
        super.onPause();
        slidingPanel.removePanelSlideListener(panelListener);
        list.removeOnScrollListener(onScrollListener);
        ingredients.setOnClickListener(null);
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == SCROLL_STATE_IDLE){
                int visiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (visiblePosition != NO_POSITION){
                    viewModel.updatePosition(visiblePosition);
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            recipeTitleList.scrollBy(dx, dy);
        }
    }

    private void toggleViewPagerTouch(boolean enabled){
        StoppableVerticalViewPager pager = requireActivity().findViewById(R.id.pager);
        pager.setSwipeEnabled(enabled);
    }

    private class SlidingPanelListener extends SlidingUpPanelLayout.SimplePanelSlideListener {

        // a cause della status bar
        private int statusBarHeight = 0;
        private int[] location = new int[]{0, 0};

        public SlidingPanelListener() {
            statusBarHeight = WindowUtils.getStatusBarHeight(getResources());
        }

        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            toggleViewPagerTouch(newState == COLLAPSED || newState == HIDDEN);
        }

        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            float translationY = (float) (headerWrapper.getHeight() * slideOffset * 1.5);
            headerWrapper.setTranslationY(-translationY);
            arrow.setAlpha(MathUtils.clamp(1 - slideOffset * 3f, 0f, 1f));

            float alpha = slideOffset;
            divider.setAlpha(alpha);
            midWrapper.setAlpha(alpha);
            bottomWrapper.setAlpha(alpha);
            descriptionWrapper.setAlpha(alpha);

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
            ParallaxScrimImageView image = (ParallaxScrimImageView) root.getChildAt(0);
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
