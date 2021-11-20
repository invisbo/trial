package com.newactivity.ui.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.network.models.HeadLineModel
import com.newactivity.databinding.ItemHeadlineBinding
import com.newactivity.databinding.ItemPagerBinding
import com.newactivity.utils.PrefUtil

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var pagerItems = mutableListOf<HeadLineModel>()
    private var items = mutableListOf<HeadLineModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_PAGER -> PagerHolder(
                ItemPagerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ItemHolder(
                ItemHeadlineBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PagerHolder) holder.bind()
        if (holder is ItemHolder) holder.bind()
    }

    override fun getItemCount(): Int {
        return items.size + (if (pagerItems.isNotEmpty()) 1 else 0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_PAGER else ITEM_NEWS
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<HeadLineModel>) {
        items.apply {
            pagerItems = if (size < 3) {
                this
            } else {
                subList(0, 3)
            }
            if (size > 3) {
                this@NewsAdapter.items = subList(3, size)
            }
        }
        notifyDataSetChanged()
    }

    inner class PagerHolder internal constructor(val binding: ItemPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val pagerAdapter = PagerAdapter()
        private val runnable = Runnable {
            binding.viewPagerTopNews.apply {
                if ((currentItem + 1) < pagerAdapter.items.size) {
                    setCurrentItem(currentItem + 1, true)
                } else setCurrentItem(0, true)

            }
        }
        private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.viewPagerTopNews.removeCallbacks(runnable)
                binding.viewPagerTopNews.postDelayed(runnable, 5000)
            }
        }

        init {
            binding.viewPagerTopNews.adapter = pagerAdapter
            binding.viewPagerTopNews.offscreenPageLimit = 2

            binding.viewPagerTopNews.unregisterOnPageChangeCallback(pageChangeCallback)
            binding.viewPagerTopNews.registerOnPageChangeCallback(pageChangeCallback)

            TabLayoutMediator(
                binding.indicatorLayout,
                binding.viewPagerTopNews
            ) { _, _ -> }.attach()
        }

        fun bind() {
            pagerAdapter.items = pagerItems
            pageChangeCallback.onPageSelected(0)
        }
    }


    inner class ItemHolder internal constructor(private val binding: ItemHeadlineBinding) :
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
            val headLine = items[adapterPosition - 1]
            binding.headLine = headLine
            binding.isInReadList = PrefUtil.getReadList(itemView.context).contains(headLine.url)
        }
    }

    companion object {
        const val ITEM_PAGER = 0
        const val ITEM_NEWS = 1
    }
}