package com.example.aslcodingtestproject.common.mapper

import com.example.aslcodingtestproject.data.remote.resp.PhotoCommentRespItem
import com.example.aslcodingtestproject.data.remote.resp.PhotoRespItem
import com.example.aslcodingtestproject.presentation.photocomment.PhotoCommentItem
import com.example.aslcodingtestproject.presentation.photos.PhotoItem

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

fun photoCommentRespItemListToPhotoCommentItemList(photoRespItemList: List<PhotoCommentRespItem>): List<PhotoCommentItem> {
    val photoCommentItemList = photoRespItemList.map {
        PhotoCommentItem(
            body = it.body,
            id = it.id
        )
    }
    return photoCommentItemList
}