package com.example.aslcodingtestproject.view.viewdata

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


