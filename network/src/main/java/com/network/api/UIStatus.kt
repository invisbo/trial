package com.network.api

enum class UIStatus {
    SUCCESS,
    ERROR,
    LOADING;

    fun isSuccessful() = this == SUCCESS

    /**
     * Returns `true` if the [Status] is loading else `false`.
     */
    fun isLoading() = this == LOADING

    /**
     * Returns `true` if the [Status] is in error else `false`.
     */
    fun isError() = this == ERROR
}