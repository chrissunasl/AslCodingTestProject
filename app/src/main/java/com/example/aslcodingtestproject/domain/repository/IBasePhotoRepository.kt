package com.example.aslcodingtestproject.domain.repository

import com.example.aslcodingtestproject.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.data.remote.resp.PhotoRespItem

// interface
interface IBasePhotoRepository {

    suspend fun insertPhoto(photo: List<PhotoDatabaseItem>)

    suspend fun getPhotoFromDb() : List<PhotoDatabaseItem>

    suspend fun getPhotoFromApi(): Resource<List<PhotoRespItem>>

}