package com.network.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseHttpClient @Inject constructor(@ApplicationContext val appContext: Context) {
    companion object {
        const val TOKEN_KEY = "X-Api-Key"
    }

    private val okHttpClientBuilder = OkHttpClient.Builder()

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor)

        okHttpClientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .build()
            val request = original.newBuilder()
                .addHeader(
                    TOKEN_KEY, "22f6cdf35429454684baf5e8fb167568"
                )

                .url(url).build()
            chain.proceed(request)
        }

    }

    val okHttpClient = okHttpClientBuilder.build()
}