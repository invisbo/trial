package com.newactivity.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.network.api.UIStatus
import com.newactivity.MainActivity
import com.newactivity.R
import com.newactivity.databinding.FragmentNewsListBinding
import com.newactivity.ui.base.BaseFragment
import com.newactivity.ui.news.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : BaseFragment<FragmentNewsListBinding>() {
    private val viewModel by viewModels<NewsListViewModel>()
    private val newsAdapter = NewsAdapter()

    override fun getLayoutRes(): Int = R.layout.fragment_news_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).actionBar.apply {
            title = arguments?.getString(KEY_SOURCE_NAME)
            isPreviousExist = true
        }
        arguments?.getString(KEY_SOURCE_ID)?.let {
            loadData(it)
        }
    }


    private fun loadData(sourceId: String) {
        viewModel.getHeadLines(sourceId).observe(viewLifecycleOwner) {
            if (it.status == UIStatus.SUCCESS) {
                it.data?.let { response ->
                    newsAdapter.setItems(response.articles)
                }
            }
        }
    }

    companion object {
        const val KEY_SOURCE_ID = "sourceId"
        const val KEY_SOURCE_NAME = "sourceName"
    }

    override fun initViews() {
        initList()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initList() {
        binding.recyclerViewNews.apply {
            adapter = newsAdapter
            val itemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_news_list))
            addItemDecoration(itemDecoration)
        }
    }
}