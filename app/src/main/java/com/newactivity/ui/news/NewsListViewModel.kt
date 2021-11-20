package com.newactivity.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.network.api.NetworkResource
import com.network.api.Resource
import com.network.api.RestService
import com.network.responses.HeadLineResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val restService: RestService) : ViewModel() {

    fun getHeadLines(sourceId: String): LiveData<Resource<HeadLineResponse>> {
        return object : NetworkResource<HeadLineResponse>() {
            override fun createCall() = restService.getHeadLines(sourceId)
        }.asLiveData()
    }
}