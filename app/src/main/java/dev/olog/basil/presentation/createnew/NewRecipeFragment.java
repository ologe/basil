package dev.olog.basil.presentation.createnew;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveDataReactiveStreams;
import dev.olog.basil.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class NewRecipeFragment extends Fragment {

    private TextView recipeName;
    private TextView recipeDescription;
    private TextView recipeCalories;
    private TextView recipePeople;

    private ChipGroup tags;
    private Chip newTag;
    private View save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newTag = view.findViewById(R.id.newTag);
        tags = view.findViewById(R.id.tags);
        save = view.findViewById(R.id.save);

        recipeName = view.findViewById(R.id.name);
        recipeDescription = view.findViewById(R.id.description);
        recipeCalories = view.findViewById(R.id.calories);
        recipePeople = view.findViewById(R.id.people);

        LiveDataReactiveStreams.fromPublisher(
                Flowable.combineLatest(
                        requireNonEmptyEditText(recipeName),
                        requireNonEmptyEditText(recipeDescription),
                        (name, description) -> name && description
                )
        ).observe(getViewLifecycleOwner(), show -> {
            int visibility = show ? View.VISIBLE : View.GONE;
            save.setVisibility(visibility);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        newTag.setOnClickListener(v -> showAddTagDialog());
//        save.setOnClickListener(v -> );
    }

    @Override
    public void onPause() {
        super.onPause();
        newTag.setOnClickListener(null);
        save.setOnClickListener(null);
    }

    private void showAddTagDialog(){
        View view = getLayoutInflater().inflate(R.layout.layout_edit_text, null, false);
        EditText editText = view.findViewById(R.id.editText);

        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.add_tag)
                .setView(view)
                .setPositiveButton(R.string.popup_add, (dialog, which) -> addTag(editText.getText().toString()))
                .setNegativeButton(R.string.popup_cancel, null)
                .show();
    }

    private void addTag(String tag){
        Chip chip = new Chip(requireContext());
        chip.setText(tag);
        tags.addView(chip, 1);
    }

    private Flowable<Boolean> requireNonEmptyEditText(TextView textView){
        return RxTextView.afterTextChangeEvents(textView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(TextViewAfterTextChangeEvent::editable)
                .map(editable -> !TextUtils.isEmpty(editable))
                .toFlowable(BackpressureStrategy.LATEST);
    }

}
