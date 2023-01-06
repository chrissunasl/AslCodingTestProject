package com.example.aslcodingtestproject.common.mapper

import com.example.aslcodingtestproject.displayphotos.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.displayphotos.data.remote.resp.PhotoCommentRespItem
import com.example.aslcodingtestproject.displayphotos.data.remote.resp.PhotoRespItem
import com.example.aslcodingtestproject.displayphotos.presentation.photocomment.PhotoCommentItem
import com.example.aslcodingtestproject.displayphotos.presentation.photos.PhotoItem

fun photoRespItemListToPhotoItemList(photoRespItemList: List<PhotoRespItem>): List<PhotoItem> {
    val photoItemList = photoRespItemList.sortedBy { it.title }.map {
        PhotoItem(
            id = it.id,
            thumbnailUrl = it.thumbnailUrl,
            title = it.title,
            url = it.url
        )
    }
    return photoItemList
}

fun photoRespItemListToPhotoDatabaseItemList(photoRespItemList: List<PhotoRespItem>): List<PhotoDatabaseItem> {
    val photoItemList = photoRespItemList.sortedBy { it.title }.map {
        PhotoDatabaseItem(
            albumId = it.albumId,
            id = it.id,
            thumbnailUrl = it.thumbnailUrl,
            title = it.title,
            url = it.url
        )
    }
    return photoItemList
}

fun photoDatabaseItemListToPhotoItemList(photoDatabaseItemList: List<PhotoDatabaseItem>): List<PhotoItem> {
    val photoItemList = photoDatabaseItemList.sortedBy { it.title }.map {
        PhotoItem(
            id = it.id,
            thumbnailUrl = it.thumbnailUrl,
            title = it.title,
            url = it.url
        )
    }
    return photoItemList
}

fun photoCommentRespItemListToPhotoCommentItemList(photoRespItemList: List<PhotoCommentRespItem>): List<PhotoCommentItem> {
    val photoCommentItemList = photoRespItemList.map {
        PhotoCommentItem(
            body = it.body,
            id = it.id
        )
    }
    return photoCommentItemList
}