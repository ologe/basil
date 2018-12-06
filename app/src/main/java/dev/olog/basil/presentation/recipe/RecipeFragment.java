package dev.olog.basil.presentation.recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dev.olog.basil.R;
import dev.olog.basil.app.GlideApp;
import dev.olog.basil.presentation.base.BaseFragment;
import dev.olog.basil.presentation.main.MainFragmentViewModel;
import dev.olog.basil.presentation.navigator.Navigator;

public class RecipeFragment extends BaseFragment {

    private static final String TAG = RecipeFragment.class.getSimpleName();
    private static final String ARGUMENT_ID = TAG + ".argument.id";

    public static RecipeFragment newInstance(long id){
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ImageView image;

    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject Navigator navigator;

    private MainFragmentViewModel viewModel;

    private long recipeId;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView title = view.findViewById(R.id.title);
        image = view.findViewById(R.id.image);

        recipeId = getArguments().getLong(ARGUMENT_ID);

        viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainFragmentViewModel.class);

//        viewModel.observeRecipeById(recipeId)
//                .observe(getViewLifecycleOwner(), recipe -> {
//                    title.setText(recipe.getName());
//                });

        GlideApp.with(view)
                .load(R.drawable.spinach)
                .into(image);
    }

    @Override
    public void onResume() {
        super.onResume();
        image.setOnClickListener(v -> navigator.toDetailFragment(recipeId));
    }

    @Override
    public void onPause() {
        super.onPause();
        image.setOnClickListener(null);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_recipe;
    }
}
