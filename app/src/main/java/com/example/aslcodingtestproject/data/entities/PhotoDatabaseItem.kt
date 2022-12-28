package com.example.aslcodingtestproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Entity for room database
@Entity
data class PhotoDatabaseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
): Serializable {
    @PrimaryKey
    var pId: Int = id
}


