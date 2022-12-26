package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// interface
interface BasePhotoRepository {

    suspend fun insertPhoto(photo: PhotoDatabaseItem)

    fun getPhotoFromDb() : LiveData<MutableList<PhotoDatabaseItem>>

    suspend fun getPhotoFromApi():  Resource<ArrayList<GetPhotoRespItem>>

}