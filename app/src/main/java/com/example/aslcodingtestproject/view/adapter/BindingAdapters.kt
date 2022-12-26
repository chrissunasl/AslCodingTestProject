package com.example.aslcodingtestproject.view.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.aslcodingtestproject.R

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    if (imgUrl != null) {
        imgView.load(imgUrl.toUri().buildUpon().scheme("https").build()) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }else{
        imgView.load(R.drawable.ic_broken_image)
    }
}

@BindingAdapter("imageTitle")
fun bindTitle(textView: TextView, displayString: String?) {
    textView.text = displayString
}

