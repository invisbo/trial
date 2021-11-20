package com.newactivity.ui.sources.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.newactivity.R
import com.newactivity.databinding.ItemSourceCategoryBinding

class SourceCategoryAdapter(context: Context, private val selector: (String?) -> Unit) :
    RecyclerView.Adapter<SourceCategoryAdapter.ItemHolder>() {
    val items = mapCategories(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemHolder {
        return ItemHolder(
            ItemSourceCategoryBinding.inflate(
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

    private fun mapCategories(context: Context): ArrayList<CategoryModel> {
        val list = ArrayList<CategoryModel>()
        val items = context.resources.getStringArray(R.array.source_categories).toMutableList()
        items.forEach {
            list.add(CategoryModel(it, false))
        }
        return list
    }

    inner class ItemHolder internal constructor(private val binding: ItemSourceCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                binding.category?.let { item ->
                    items.forEach {
                        if (it == item) {
                            item.selected = item.selected.not()
                        } else {
                            it.selected = false
                        }
                    }

                    if (item.selected) {
                        selector.invoke(item.category)
                    } else {
                        selector.invoke(null)
                    }
                }
            }
        }

        fun bind() {
            binding.category = items[adapterPosition]
        }
    }

    class CategoryModel(category: String, selected: Boolean) : BaseObservable() {
        @Bindable
        var category: String = category
            set(firstName) {
                field = firstName
                notifyPropertyChanged(BR.category)
            }

        @Bindable
        var selected: Boolean = selected
            set(isSelected) {
                field = isSelected
                notifyPropertyChanged(BR.selected)
            }
    }
}