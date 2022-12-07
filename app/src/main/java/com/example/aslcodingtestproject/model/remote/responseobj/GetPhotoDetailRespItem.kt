package com.example.aslcodingtestproject.model.remote.responseobj

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

// Get photo detail list(comment) response Item

@Entity
@Parcelize
data class GetPhotoDetailRespItem(
    val body: String,
    val email: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val postId: Int
): Serializable, Parcelable