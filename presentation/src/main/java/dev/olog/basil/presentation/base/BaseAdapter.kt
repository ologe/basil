package dev.olog.basil.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import dev.olog.basil.presentation.model.BaseModel

abstract class BaseAdapter<T : BaseModel>
    : RecyclerView.Adapter<DataBoundViewHolder>() {

    protected val dataSet: MutableList<T> = mutableListOf()

    val isEmpty: Boolean = dataSet.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        val viewHolder = DataBoundViewHolder(view)
        initViewHolderListeners(viewHolder, viewType)
        return viewHolder
    }

    protected abstract fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int)

    override fun onBindViewHolder(holder: DataBoundViewHolder, position: Int) {
        val item = dataSet[position]
        bind(holder, item, position)
    }

    protected abstract fun bind(holder: DataBoundViewHolder, item: T, position: Int)

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type
    }

    @CallSuper
    override fun onViewAttachedToWindow(holder: DataBoundViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: DataBoundViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }

    override fun getItemCount(): Int = dataSet.size

    open fun updateDataSet(newData: List<T>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

}
