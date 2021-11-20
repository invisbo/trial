package com.newactivity.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {
    Glide.with(this).load(url).centerCrop().into(this)
}