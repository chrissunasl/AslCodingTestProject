package com.example.aslcodingtestproject.constant.util

import android.view.View

interface OnCustomItemClickListener<T> {
    fun onClick(view: View?, item: T)
}