package com.example.aslcodingtestproject.model.repository.api

import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performPhotoOperation
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.service.PhotoService
import com.example.aslcodingtestproject.model.repository.BasePhotoDetailRepository
import javax.inject.Inject

// get data
class PhotoDetailRepository @Inject constructor(
    private val apiService: PhotoService,
): BasePhotoDetailRepository {

    override suspend fun getPhotoDetailFromApi(id: String): Resource<List<GetPhotoDetailRespItem>> {
        return performPhotoOperation(
            networkCall = {
                apiService.getImgDetail(id = id)
            },
        )
    }

}