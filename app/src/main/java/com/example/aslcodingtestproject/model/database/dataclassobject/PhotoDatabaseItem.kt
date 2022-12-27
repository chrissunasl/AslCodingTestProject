package com.example.aslcodingtestproject.model.database.dataclassobject

import androidx.room.Entity
import androidx.room.PrimaryKey

// Get photo list response Item
@Entity
data class PhotoDatabaseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String = "",
    val title: String = "",
    val url: String = ""
){
    @PrimaryKey(autoGenerate = false)
    var pId: Int = id
}


