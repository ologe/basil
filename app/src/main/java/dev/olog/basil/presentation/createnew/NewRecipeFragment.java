package dev.olog.basil.presentation.createnew;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.olog.basil.R;
import dev.olog.basil.app.GlideApp;
import dev.olog.basil.domain.entity.Ingredient;
import dev.olog.basil.domain.entity.Recipe;
import dev.olog.basil.domain.entity.Tag;
import dev.olog.basil.presentation.DrawsOnTop;
import dev.olog.basil.presentation.base.BaseFragment;

public class NewRecipeFragment extends BaseFragment implements DrawsOnTop {

    private static final int IMAGE_CODE = 20;

    public static final String TAG = NewRecipeFragment.class.getSimpleName();

    private View back;
    private TextView recipeName;
    private TextView recipeDescription;
    private TextView recipeCalories;
    private TextView recipePeople;

    private ChipGroup tags;
    private View save;

    private View addTag;
    private View addIngredient;
    private View addImage;
    private ImageView image;

    private NewIngredientAdapter adapter;

    @Inject NewRecipePresenter presenter;

    @Override
    protected void onViewBound(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tags = view.findViewById(R.id.tags);
        save = view.findViewById(R.id.save);
        back = view.findViewById(R.id.back);
        RecyclerView ingredientsList = view.findViewById(R.id.ingredientsList);

        recipeName = view.findViewById(R.id.name);
        recipeDescription = view.findViewById(R.id.description);
        recipeCalories = view.findViewById(R.id.calories);
        recipePeople = view.findViewById(R.id.people);
        addTag = view.findViewById(R.id.addTag);
        addIngredient = view.findViewById(R.id.addIngredient);
        addImage = view.findViewById(R.id.changeImage);
        image = view.findViewById(R.id.image);

        ingredientsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new NewIngredientAdapter();
        ingredientsList.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        addTag.setOnClickListener(v -> showAddTagDialog());
        addIngredient.setOnClickListener(v -> showAddIngredientDialog());
        addImage.setOnClickListener(v -> changeImage());
        save.setOnClickListener(v -> {
            if (!checkRequiredFieldNotEmpty()){
                return;
            }

            List<Ingredient> ingredients = new ArrayList<>();
            List<Tag> tags = new ArrayList<>();
            for (int i = 0; i < this.tags.getChildCount(); i++) {
                tags.add(new Tag(0, ((Chip) this.tags.getChildAt(i)).getText().toString()));
            }
            List<String> images = new ArrayList<>();
            images.add(presenter.getImage().toString());
            int calories = TextUtils.isEmpty(recipeCalories.getText().toString()) ? 0 :
                    Integer.parseInt(recipeCalories.getText().toString());
            presenter.saveRecipe(new Recipe(
                    0,
                    recipeName.getText().toString(),
                    recipeDescription.getText().toString(),
                    Integer.parseInt(recipePeople.getText().toString()),
                    calories,
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
        save.setOnClickListener(null);
        addTag.setOnClickListener(null);
        addIngredient.setOnClickListener(null);
        addImage.setOnClickListener(null);
        back.setOnClickListener(null);
    }

    private boolean checkRequiredFieldNotEmpty(){
        if (TextUtils.isEmpty(recipeName.getText().toString())){
            Toast.makeText(requireContext(), getString(R.string.need_recipe_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(recipeDescription.getText().toString())){
            Toast.makeText(requireContext(), getString(R.string.need_recipe_description), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(recipePeople.getText().toString())){
            Toast.makeText(requireContext(), getString(R.string.need_recipe_people), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (adapter.isEmpty()){
            Toast.makeText(requireContext(), getString(R.string.need_recipe_ingredient), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!presenter.isImageSet()){
            Toast.makeText(requireContext(), getString(R.string.need_recipe_image), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void changeImage(){
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_CODE);
    }

    private void showAddIngredientDialog(){
        View view = getLayoutInflater().inflate(R.layout.layout_new_ingredient, null, false);
        EditText name = view.findViewById(R.id.name);
        EditText quantity = view.findViewById(R.id.quantity);

        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.add_ingredient)
                .setView(view)
                .setPositiveButton(R.string.popup_add, (dialog, which) -> addIngredient(name.getText().toString(), quantity.getText().toString()))
                .setNegativeButton(R.string.popup_cancel, null)
                .show();
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
        tags.addView(chip, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GlideApp.with(requireContext())
                .load(data.getData())
                .into(image);
        presenter.setImage(data.getData());
    }

    private void addIngredient(String name, String quantity){
        Ingredient ingredient = new Ingredient(0, name, Integer.parseInt(quantity), adapter.getItemCount() + 1);
        adapter.addIngredient(ingredient);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_new_recipe;
    }

}
