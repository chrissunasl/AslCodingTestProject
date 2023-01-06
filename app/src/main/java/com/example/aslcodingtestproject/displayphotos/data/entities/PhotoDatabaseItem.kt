package com.example.aslcodingtestproject.displayphotos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Entity for room database
@Entity
data class PhotoDatabaseItem(
    @PrimaryKey
    val id: Int,
    val albumId: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
): Serializable


