package dev.olog.basil.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<DataBoundViewHolder>() {

    private var dataSet: List<T> = listOf()

    val isEmpty: Boolean = dataSet.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, provideLayoutId(), parent, false)
        val viewHolder = DataBoundViewHolder(binding)
        initViewHolderListeners(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder, position: Int) {
        val item = dataSet[position]
        bind(holder.binding, item, position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    open fun updateDataSet(newData: List<T>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    protected abstract fun initViewHolderListeners(binding: ViewDataBinding)

    protected abstract fun bind(binding: ViewDataBinding, item: T, position: Int)


    @LayoutRes
    protected abstract fun provideLayoutId(): Int

}
