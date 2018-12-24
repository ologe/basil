package dev.olog.basil.presentation.createnew;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveDataReactiveStreams;
import dev.olog.basil.R;
import dev.olog.basil.domain.entity.Ingredient;
import dev.olog.basil.domain.entity.Recipe;
import dev.olog.basil.domain.entity.Tag;
import dev.olog.basil.presentation.DrawsOnTop;
import dev.olog.basil.presentation.base.BaseFragment;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class NewRecipeFragment extends BaseFragment implements DrawsOnTop {

    public static final String TAG = NewRecipeFragment.class.getSimpleName();

    private View back;
    private TextView recipeName;
    private TextView recipeDescription;
    private TextView recipeCalories;
    private TextView recipePeople;

    private ChipGroup tags;
    private Chip newTag;
    private View save;

    @Inject NewRecipePresenter presenter;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newTag = view.findViewById(R.id.newTag);
        tags = view.findViewById(R.id.tags);
        save = view.findViewById(R.id.save);
        back = view.findViewById(R.id.back);

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
        save.setOnClickListener(v -> {
            List<Ingredient> ingredients = new ArrayList<>();
            List<Tag> tags = new ArrayList<>();
            for (int i = 0; i < this.tags.getChildCount(); i++) {
                tags.add(new Tag(0, ((Chip) this.tags.getChildAt(i)).getText().toString()));
            }
            List<String> images = new ArrayList<>();
            presenter.saveRecipe(new Recipe(
                    0,
                    recipeName.getText().toString(),
                    recipeDescription.getText().toString(),
                    Integer.parseInt(recipePeople.getText().toString()),
                    Integer.parseInt(recipeCalories.getText().toString()),
                    ingredients,
                    images,
                    tags
            ));
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        back.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    @Override
    public void onPause() {
        super.onPause();
        newTag.setOnClickListener(null);
        save.setOnClickListener(null);
        back.setOnClickListener(null);
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

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_new_recipe;
    }

}
