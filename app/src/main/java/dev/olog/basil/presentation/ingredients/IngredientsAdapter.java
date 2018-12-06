package dev.olog.basil.presentation.ingredients;

import java.util.Collections;
import java.util.List;

import androidx.databinding.ViewDataBinding;
import dev.olog.basil.BR;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseAdapter;
import dev.olog.basil.presentation.model.DisplayableIngredient;

public class IngredientsAdapter extends BaseAdapter<DisplayableIngredient> {

    @Override
    protected void initViewHolderListeners(ViewDataBinding binding) {

    }

    @Override
    protected void bind(ViewDataBinding binding, DisplayableIngredient item, int position) {
        binding.setVariable(BR.item, item);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.item_ingredient;
    }

    @Override
    public void updateDataSet(List<DisplayableIngredient> newData) {
        Collections.sort(newData, (o1, o2) -> o1.getOrder() - o2.getOrder());
        super.updateDataSet(newData);
    }
}
