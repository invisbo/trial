package com.network.api

import androidx.lifecycle.LiveData
import com.network.responses.HeadLineResponse
import com.network.responses.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

    @GET("top-headlines/sources?language=en")
    fun getSources(@Query("category") category: String? = null): LiveData<ApiResponse<SourceResponse>>

    @GET("top-headlines")
    fun getHeadLines(@Query("sources") sourceId: String): LiveData<ApiResponse<HeadLineResponse>>
}