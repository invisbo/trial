package com.newactivity.ui.sources

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.network.api.UIStatus
import com.newactivity.MainActivity
import com.newactivity.R
import com.newactivity.databinding.FragmentNewsSourcesBinding
import com.newactivity.ui.base.BaseFragment
import com.newactivity.ui.news.NewsListFragment
import com.newactivity.ui.sources.adapters.SourceAdapter
import com.newactivity.ui.sources.adapters.SourceCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSourcesFragment : BaseFragment<FragmentNewsSourcesBinding>() {
    private val viewModel by viewModels<NewsSourcesViewModel>()
    private val adapter = SourceAdapter()

    override fun getLayoutRes(): Int = R.layout.fragment_news_sources

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).actionBar.apply {
            title = getString(R.string.source_title)
            isPreviousExist = false
        }

        if (adapter.items.isNullOrEmpty()) {
            loadData()
        }

        adapter.onClick = {
            val bundle = bundleOf(
                NewsListFragment.KEY_SOURCE_ID to it.id,
                NewsListFragment.KEY_SOURCE_NAME to it.name
            )
            findNavController().navigate(R.id.navigateHeadLines, bundle)
            (requireActivity() as MainActivity).actionBar.previousTitle =
                getString(R.string.source_title)
        }
    }

    override fun initViews() {
        initCategories()
        initSourceItems()
    }

    private fun loadData(category: String? = null) {
        viewModel.getSources(category).observe(viewLifecycleOwner) {
            if (it.status == UIStatus.SUCCESS) {
                it.data?.let { response ->
                    adapter.items = response.sources
                }
            }
        }
    }

    private fun initCategories() {
        binding.recyclerViewCategories.apply {
            adapter = SourceCategoryAdapter(requireContext()) {
                loadData(it)
            }

            val itemDecoration = object : DividerItemDecoration(requireContext(), HORIZONTAL) {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    val padding = resources.getDimension(R.dimen.app_padding)
                        .toInt()
                    if (position == 0) {
                        outRect.left = padding
                    }
                    outRect.right = padding
                }
            }
            addItemDecoration(itemDecoration)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initSourceItems() {
        binding.recyclerViewSources.apply {
            adapter = this@NewsSourcesFragment.adapter
            val itemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_source_list, null))
            addItemDecoration(itemDecoration)
        }
    }
}