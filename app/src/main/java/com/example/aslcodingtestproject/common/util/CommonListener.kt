package com.example.aslcodingtestproject.common.util

import android.view.View

interface OnCustomItemClickListener<T> {
    // Onclick event return view & related obj/any
    fun onClick(view: View, item: T)
}