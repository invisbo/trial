package com.network.models

import java.text.SimpleDateFormat
import java.util.*

class HeadLineModel(
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
) {
    fun getFormattedDate(): String {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(publishedAt)

        return date?.let {
            android.text.format.DateFormat.format("HH-mm:ss", it).toString()
        } ?: kotlin.run {
            ""
        }
    }
}

