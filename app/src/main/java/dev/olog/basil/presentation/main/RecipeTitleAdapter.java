package dev.olog.basil.presentation.main;

import androidx.databinding.ViewDataBinding;
import dev.olog.basil.BR;
import dev.olog.basil.R;
import dev.olog.basil.presentation.base.BaseAdapter;

public class RecipeTitleAdapter extends BaseAdapter<String> {

    @Override
    protected void initViewHolderListeners(ViewDataBinding binding) {

    }

    @Override
    protected void bind(ViewDataBinding binding, String item, int position) {
        binding.setVariable(BR.item, item);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.item_recipe_title;
    }
}
