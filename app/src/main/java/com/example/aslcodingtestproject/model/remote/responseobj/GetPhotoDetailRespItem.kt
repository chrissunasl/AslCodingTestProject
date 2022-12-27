package com.example.aslcodingtestproject.model.remote.responseobj

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Get photo detail list(comment) response Item
@Parcelize
data class GetPhotoDetailRespItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
): Parcelable