package com.example.aslcodingtestproject.model.repository

import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem

// interface
interface BasePhotoDetailRepository {

    suspend fun getPhotoDetailFromApi(id: String):  Resource<List<GetPhotoDetailRespItem>>

}