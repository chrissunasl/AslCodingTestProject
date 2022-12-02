package com.example.aslcodingtestproject.model.remote.responseobj

data class GetPhotoRespItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)