package com.example.aslcodingtestproject.displayphotos.domain.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.displayphotos.presentation.photocomment.PhotoCommentItem

// interface
interface IPhotoCommentRepository {

    suspend fun getPhotoComments(id: String): Resource<List<PhotoCommentItem>>

}