package com.example.aslcodingtestproject.data.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.common.mapper.photoCommentRespItemListToPhotoCommentItemList
import com.example.aslcodingtestproject.data.performPhotoOperation
import com.example.aslcodingtestproject.data.remote.api.PhotoService
import com.example.aslcodingtestproject.domain.repository.IBasePhotoCommentRepository
import com.example.aslcodingtestproject.presentation.photocomment.PhotoCommentItem
import javax.inject.Inject

// get data
class PhotoCommentRepository @Inject constructor(
    private val apiService: PhotoService,
): IBasePhotoCommentRepository {

    override suspend fun getPhotoComments(id: String): Resource<List<PhotoCommentItem>> {
        val res = performPhotoOperation(
            networkCall = {
                apiService.getImgComment(id = id)
            }
        )
        return if(res.data != null){
            Resource.success(photoCommentRespItemListToPhotoCommentItemList(res.data))
        }else{
            Resource.error("Error", null)
        }
    }

}