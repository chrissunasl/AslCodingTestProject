package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// interface
interface BasePhotoDetailRepository {

    suspend fun insertPhotoDetail(photoDetailList: ArrayList<GetPhotoDetailRespItem>)

    fun getPhotoDetailFromDb() : LiveData<MutableList<GetPhotoDetailRespItem>>

    suspend fun getPhotoDetailFromApi(id: String):  Resource<ArrayList<GetPhotoDetailRespItem>>

}