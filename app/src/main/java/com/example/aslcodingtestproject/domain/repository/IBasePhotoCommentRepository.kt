package com.example.aslcodingtestproject.domain.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.data.remote.resp.PhotoCommentRespItem

// interface
interface IBasePhotoCommentRepository {

    suspend fun getPhotoCommentFromApi(id: String): Resource<List<PhotoCommentRespItem>>

}