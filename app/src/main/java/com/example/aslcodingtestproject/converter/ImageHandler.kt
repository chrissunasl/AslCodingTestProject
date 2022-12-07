package com.example.aslcodingtestproject.converter

import android.widget.ImageView
import androidx.core.net.toUri
import coil.load
import com.example.aslcodingtestproject.R

// Image Handler
object ImageHandler {
    fun bindImageWithUrl(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            imgView.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
        }
    }
}