package dev.olog.basil.presentation;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import dev.olog.basil.R;
import dev.olog.basil.app.GlideApp;
import dev.olog.basil.presentation.model.DisplayableMiniRecipe;

public class BindingsAdapter {

    @BindingAdapter(value = "loadRecipeImage")
    public static void loadRecipeImage(ImageView imageView, DisplayableMiniRecipe recipe){
        String image = recipe.getImage();
        if (TextUtils.isEmpty(image)){
            int drawableId;
            if (recipe.getId() % 2 == 0){
                drawableId = R.drawable.pasta;
            } else {
                drawableId = R.drawable.salmon;
            }
            GlideApp.with(imageView.getContext())
                    .load(drawableId)
                    .into(imageView);
        } else {
            GlideApp.with(imageView.getContext())
                    .load(recipe.getImage())
                    .into(imageView);
        }
    }

}
