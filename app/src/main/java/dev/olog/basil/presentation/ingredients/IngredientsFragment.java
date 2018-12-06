package dev.olog.basil.presentation.ingredients;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.NoBackgroundBottomSheet;
import dev.olog.basil.presentation.main.MainFragmentViewModel;

public class IngredientsFragment extends BottomSheetDialogFragment {

    public static final String TAG = IngredientsFragment.class.getSimpleName();
    public static final String ARGUMENT_ID = TAG + ".arguments.id";

    public static IngredientsFragment newInstance(long id){
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Inject ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new NoBackgroundBottomSheet(requireContext(), getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MainFragmentViewModel viewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainFragmentViewModel.class);
        IngredientsAdapter adapter = new IngredientsAdapter();

        RecyclerView list = view.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.observeCurrentIngredient()
                .observe(getViewLifecycleOwner(), adapter::updateDataSet);
    }
}
