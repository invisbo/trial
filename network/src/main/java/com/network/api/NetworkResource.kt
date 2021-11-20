package com.network.api

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkResource<RequestType> @MainThread constructor() {

    /**
     * The final result LiveData
     * MediatorLiveData is a LiveData subclass which may observe
     * other LiveData objects and react on OnChanged events from them.
     *
     * This class correctly propagates its active/inactive states down to source LiveData objects.
     */
    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        // Send loading state to UI
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    /**
     * Fetch the data from network and then send it upstream to UI.
     */
    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        // Make the network call
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            // Dispatch the result

            when (response) {
                is ApiSuccessResponse -> {

                    setValue(Resource.success(response.body))

                }
                is ApiEmptyResponse -> {
                    setValue(Resource.error(networkError = NetworkError("Empty"), data = null))

                }
                is ApiErrorResponse -> {
                    setValue(
                        Resource.error(
                            networkError = NetworkError(
                                response.errorMessage,
                                response.errorCode ?: 0
                            ), data = null
                        )
                    )
                }
            }

        }
    }

    fun asLiveData(): LiveData<Resource<RequestType>> = result


    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue)
            result.value = newValue
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}