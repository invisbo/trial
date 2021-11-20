package com.network.api

import com.network.api.UIStatus.*

data class Resource<out T>(val status: UIStatus, val data: T?, val networkError: NetworkError?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(data: T?, networkError: NetworkError?): Resource<T> {
            return Resource(ERROR, data, networkError)
        }

        fun <T> loading(data: T?): Resource<T>? {
            return Resource(LOADING, data, null)
        }
    }
}

class NetworkError(val message: String?, val errorCode: Int = 0)