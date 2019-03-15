package com.example.test.extensions

import android.view.View

fun View.setVisibility(visible: Boolean) {
    visibility = if(visible) View.VISIBLE else View.GONE
}