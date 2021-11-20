package com.newactivity.extensions

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isActivated")
fun View.activate(activate: Boolean) {
    this.isActivated = activate
}

@BindingAdapter("show")
fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}
