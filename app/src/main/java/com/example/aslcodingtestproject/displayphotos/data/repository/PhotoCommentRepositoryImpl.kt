package com.example.aslcodingtestproject.displayphotos.data.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.common.mapper.photoCommentRespItemListToPhotoCommentItemList
import com.example.aslcodingtestproject.displayphotos.data.performPhotoOperation
import com.example.aslcodingtestproject.displayphotos.data.remote.api.PhotoService
import com.example.aslcodingtestproject.displayphotos.domain.repository.IPhotoCommentRepository
import com.example.aslcodingtestproject.displayphotos.presentation.photocomment.PhotoCommentItem
import javax.inject.Inject

// get data
class PhotoCommentRepositoryImpl @Inject constructor(
    private val apiService: PhotoService,
): IPhotoCommentRepository {

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