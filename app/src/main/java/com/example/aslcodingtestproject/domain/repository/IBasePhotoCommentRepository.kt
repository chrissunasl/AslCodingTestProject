package com.example.aslcodingtestproject.domain.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.presentation.photocomment.PhotoCommentItem

// interface
interface IBasePhotoCommentRepository {

    suspend fun getPhotoComments(id: String): Resource<List<PhotoCommentItem>>

}