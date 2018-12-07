package dev.olog.basil.presentation.main;

import java.util.List;

import androidx.databinding.ViewDataBinding;
import dev.olog.basil.BR;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseAdapter;
import dev.olog.basil.presentation.model.DisplayableMiniRecipe;
import dev.olog.basil.presentation.widget.ParallaxScrimImageView;

public class RecipesViewPagerAdapter extends BaseAdapter<DisplayableMiniRecipe> {

    public RecipesViewPagerAdapter() {
    }

    public RecipesViewPagerAdapter(List<DisplayableMiniRecipe> data) {
        super(data);
    }

    @Override
    protected void initViewHolderListeners(ViewDataBinding binding) {
        ParallaxScrimImageView imageView = binding.getRoot().findViewById(R.id.image);
    }

    @Override
    protected void bind(ViewDataBinding binding, DisplayableMiniRecipe item, int position) {
        binding.setVariable(BR.recipe, item);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.item_recipe_image;
    }
}
