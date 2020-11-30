package com.vd.movies.ui.util

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun showView(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) VISIBLE else INVISIBLE
}