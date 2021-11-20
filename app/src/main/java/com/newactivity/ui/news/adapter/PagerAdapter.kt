package com.newactivity.ui.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.models.HeadLineModel
import com.newactivity.databinding.ItemHeadlinePagerBinding
import com.newactivity.utils.PrefUtil

class PagerAdapter : RecyclerView.Adapter<PagerAdapter.ItemHolder>() {
    var items = mutableListOf<HeadLineModel>()
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
            ItemHeadlinePagerBinding.inflate(
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


    inner class ItemHolder internal constructor(private val binding: ItemHeadlinePagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.textViewReadList.setOnClickListener {
                binding.headLine?.apply {
                    if (binding.isInReadList == true) {
                        PrefUtil.removeReadList(itemView.context, url)
                    } else {
                        PrefUtil.addReadList(itemView.context, url)
                    }

                    binding.isInReadList = binding.isInReadList?.not()
                }
            }
        }

        fun bind() {
            val headLine = items[adapterPosition]
            binding.headLine = headLine
            binding.isInReadList = PrefUtil.getReadList(itemView.context).contains(headLine.url)
        }
    }
}