package com.newactivity.ui.sources.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.models.SourceModel
import com.newactivity.databinding.ItemSourceBinding

class SourceAdapter : RecyclerView.Adapter<SourceAdapter.ItemHolder>() {
    var onClick: ((SourceModel) -> Unit)? = null
    var items = mutableListOf<SourceModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder {
        return ItemHolder(
            ItemSourceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size


    inner class ItemHolder internal constructor(private val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(items[adapterPosition])
            }
        }

        fun bind() {
            binding.source = items[adapterPosition]
        }
    }

}