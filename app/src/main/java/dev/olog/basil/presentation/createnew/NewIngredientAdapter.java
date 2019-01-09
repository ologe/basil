package dev.olog.basil.presentation.createnew;

import androidx.databinding.ViewDataBinding;
import dev.olog.basil.BR;
import dev.olog.basil.R;
import dev.olog.basil.domain.entity.Ingredient;
import dev.olog.basil.presentation.base.BaseAdapter;
import dev.olog.basil.presentation.model.DisplayableIngredient;

public class NewIngredientAdapter extends BaseAdapter<Ingredient> {

    @Override
    protected void initViewHolderListeners(ViewDataBinding binding) {

    }

    @Override
    protected void bind(ViewDataBinding binding, Ingredient item, int position) {
        DisplayableIngredient ingredient = new DisplayableIngredient(item.getName(), String.valueOf(item.getQuantity()), item.getOrder());
        binding.setVariable(BR.item, ingredient);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.item_ingredient;
    }

    public void addIngredient(Ingredient ingredient) {
        dataSet.add(ingredient);
        notifyDataSetChanged();
    }
}
