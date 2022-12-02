package com.example.aslcodingtestproject.model.database.structure

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class PhotoBase(
    var id: Int?,
    var dt_last_update_datetime: String?,
    @PrimaryKey
    var photo_id: Int?,
    var photo_url: String?,
){

    @Ignore
    var isDisplay:Boolean = false

}