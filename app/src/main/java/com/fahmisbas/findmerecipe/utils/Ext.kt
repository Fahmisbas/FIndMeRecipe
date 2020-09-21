package com.fahmisbas.findmerecipe.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.gone() {
    visibility = View.GONE
}

fun Context.makeToast(s: String) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}