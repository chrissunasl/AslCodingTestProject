package com.example.aslcodingtestproject.presentation.photos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

// Get photo list response Item
@Parcelize
data class PhotoItem(
    val id: Int,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
): Serializable, Parcelable


