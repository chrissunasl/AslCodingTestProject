package com.example.aslcodingtestproject.presentation.photocomment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Get photo detail list(comment) response Item
@Parcelize
data class PhotoCommentItem(
    val body: String,
    val id: Int,
): Parcelable