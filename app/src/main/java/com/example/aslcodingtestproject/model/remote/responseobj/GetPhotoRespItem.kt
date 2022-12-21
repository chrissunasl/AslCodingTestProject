package com.example.aslcodingtestproject.model.remote.responseobj

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.io.Serializable

// Get photo list response Item
@Entity
@Parcelize
data class GetPhotoRespItem(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    @Json(name = "img_src") val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
): Serializable, Parcelable


