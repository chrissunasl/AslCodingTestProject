package com.example.aslcodingtestproject.data.remote.resp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Get photo list response Item from api
@Parcelize
data class PhotoRespItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
): Parcelable


