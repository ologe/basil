package dev.olog.basil.presentation;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import dev.olog.basil.R;
import dev.olog.basil.app.GlideApp;
import dev.olog.basil.presentation.model.DisplayableMiniRecipe;

public class BindingsAdapter {

    @BindingAdapter(value = "loadRecipeImage")
    public static void loadRecipeImage(ImageView imageView, DisplayableMiniRecipe recipe){
        int imageName;
        if (recipe.getId() % 2 == 0){
            imageName = R.drawable.pasta;
        } else {
            imageName = R.drawable.salmon;
        }

//        imageView.setImageResource(imageName);
        GlideApp.with(imageView.getContext())
                .load(imageName)
                .into(imageView);
    }

}
