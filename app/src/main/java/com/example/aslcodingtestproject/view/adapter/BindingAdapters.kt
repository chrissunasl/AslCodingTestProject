package com.example.aslcodingtestproject.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.aslcodingtestproject.view.viewmanager.ImageHandler

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    ImageHandler.bindImageWithUrl(imgView, imgUrl)
}

