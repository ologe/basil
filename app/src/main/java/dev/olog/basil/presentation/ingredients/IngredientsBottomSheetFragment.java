package dev.olog.basil.presentation.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dev.olog.basil.R;

public class IngredientsBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = IngredientsBottomSheetFragment.class.getSimpleName();
    public static final String ARGUMENT_ID = TAG + ".arguments.id";

    public static IngredientsBottomSheetFragment newInstance(long id){
        Bundle bundle = new Bundle();
        bundle.putLong(ARGUMENT_ID, id);
        IngredientsBottomSheetFragment fragment = new IngredientsBottomSheetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
