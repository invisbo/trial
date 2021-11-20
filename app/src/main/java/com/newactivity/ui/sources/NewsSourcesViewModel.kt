package com.newactivity.ui.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.network.api.NetworkResource
import com.network.api.Resource
import com.network.api.RestService
import com.network.responses.SourceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(private val restService: RestService) : ViewModel() {

    fun getSources(category: String? = null): LiveData<Resource<SourceResponse>> {
        return object : NetworkResource<SourceResponse>() {
            override fun createCall() = restService.getSources(category)
        }.asLiveData()
    }
}