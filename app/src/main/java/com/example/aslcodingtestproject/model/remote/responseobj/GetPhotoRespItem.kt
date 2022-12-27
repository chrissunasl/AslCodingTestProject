package com.example.aslcodingtestproject.model.remote.responseobj

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Get photo list response Item
@Parcelize
data class GetPhotoRespItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
): Parcelable


