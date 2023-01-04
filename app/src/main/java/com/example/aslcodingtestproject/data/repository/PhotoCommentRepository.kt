package com.example.aslcodingtestproject.data.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.data.performPhotoOperation
import com.example.aslcodingtestproject.data.remote.resp.PhotoCommentRespItem
import com.example.aslcodingtestproject.data.remote.api.PhotoService
import com.example.aslcodingtestproject.domain.repository.IBasePhotoCommentRepository
import javax.inject.Inject

// get data
class PhotoCommentRepository @Inject constructor(
    private val apiService: PhotoService,
): IBasePhotoCommentRepository {

    override suspend fun getPhotoCommentFromApi(id: String): Resource<List<PhotoCommentRespItem>> {
        return performPhotoOperation(
            networkCall = {
                apiService.getImgComment(id = id)
            }
        )
    }

}