package dev.olog.basil.presentation.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter <T> extends RecyclerView.Adapter<BaseAdapter.DataBoundViewHolder> {

    protected final List<T> dataSet = new ArrayList<>();

    public BaseAdapter() {
    }

    public BaseAdapter(List<T> data){
        dataSet.addAll(data);
    }

    @NonNull
    @Override
    public DataBoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, provideLayoutId(), parent, false);
        DataBoundViewHolder viewHolder = new DataBoundViewHolder(binding);
        initViewHolderListeners(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder holder, int position) {
        T item = dataSet.get(position);
        bind(holder.getBinding(), item, position);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateDataSet(List<T> newData){
        dataSet.clear();
        dataSet.addAll(newData);
        notifyDataSetChanged();
    }

    protected abstract void initViewHolderListeners(ViewDataBinding binding);

    protected abstract void bind(ViewDataBinding binding, T item, int position);


    @LayoutRes
    protected abstract int provideLayoutId();

    public boolean isEmpty() {
        return dataSet.isEmpty();
    }

    static class DataBoundViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        DataBoundViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

}
