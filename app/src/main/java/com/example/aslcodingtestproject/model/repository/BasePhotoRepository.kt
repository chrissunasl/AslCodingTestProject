package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// interface
interface BasePhotoRepository {

    suspend fun insertPhoto(photoList: ArrayList<GetPhotoRespItem>)

    fun getPhotoFromDb() : LiveData<MutableList<GetPhotoRespItem>>

    suspend fun getPhotoFromApi():  Resource<ArrayList<GetPhotoRespItem>>

}