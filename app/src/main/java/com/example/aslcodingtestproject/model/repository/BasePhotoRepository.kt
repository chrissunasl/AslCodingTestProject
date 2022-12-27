package com.example.aslcodingtestproject.model.repository

import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// interface
interface BasePhotoRepository {

    suspend fun insertPhoto(photo: List<PhotoDatabaseItem>)

    suspend fun getPhotoFromDb() : List<PhotoDatabaseItem>

    suspend fun getPhotoFromApi():  Resource<List<GetPhotoRespItem>>

}