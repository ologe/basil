package dev.olog.basil.presentation;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import dev.olog.basil.R;
import dev.olog.basil.app.GlideApp;
import dev.olog.basil.presentation.model.DisplayableMiniRecipe;

public class BindingsAdapter {

    @BindingAdapter(value = "loadRecipeImage")
    public static void loadRecipeImage(ImageView imageView, DisplayableMiniRecipe recipe){
        GlideApp.with(imageView.getContext())
                .load(recipe.getImage())
                .into(imageView);
    }

}
