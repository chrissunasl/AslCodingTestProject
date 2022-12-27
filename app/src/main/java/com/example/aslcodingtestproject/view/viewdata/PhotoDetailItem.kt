package com.example.aslcodingtestproject.view.viewdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Get photo detail list(comment) response Item
@Parcelize
data class PhotoDetailItem(
    val body: String,
    val id: Int,
): Parcelable