package dev.olog.basil.presentation;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import dev.olog.basil.R;
import dev.olog.basil.app.GlideApp;
import dev.olog.basil.presentation.model.DisplayableRecipeImage;

public class BindingsAdapter {

    @BindingAdapter(value = "loadRecipeImage")
    public static void loadRecipeImage(ImageView imageView, DisplayableRecipeImage recipe){
        int imageName;
        if (recipe.getId() % 2 == 0){
            imageName = R.drawable.spinach;
        } else {
            imageName = R.drawable.pasta;
        }


        GlideApp.with(imageView.getContext())
                .load(imageName)
                .into(imageView);
    }

}
