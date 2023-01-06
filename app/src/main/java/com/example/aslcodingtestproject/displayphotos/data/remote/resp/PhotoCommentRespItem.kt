package com.example.aslcodingtestproject.displayphotos.data.remote.resp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Get photo comment list response Item from api
@Parcelize
data class PhotoCommentRespItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
): Parcelable