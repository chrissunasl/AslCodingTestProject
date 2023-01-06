package com.example.aslcodingtestproject.displayphotos.domain.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.displayphotos.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.displayphotos.presentation.photos.PhotoItem

// interface
interface IPhotoRepository {

    suspend fun insertPhoto(photo: List<PhotoDatabaseItem>)

    suspend fun getPhotoFromDb() : List<PhotoDatabaseItem>

    suspend fun getPhotos(): Resource<List<PhotoItem>>

}