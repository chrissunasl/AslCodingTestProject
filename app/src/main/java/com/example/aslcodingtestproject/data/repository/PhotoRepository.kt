package com.example.aslcodingtestproject.data.repository

import com.example.aslcodingtestproject.data.local.dao.PhotoDao
import com.example.aslcodingtestproject.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.domain.usecase.performPhotoOperation
import com.example.aslcodingtestproject.data.remote.resp.PhotoRespItem
import com.example.aslcodingtestproject.data.remote.api.PhotoService
import com.example.aslcodingtestproject.domain.repository.IBasePhotoRepository
import timber.log.Timber
import javax.inject.Inject

// get data
class PhotoRepository @Inject constructor(
    private val apiService: PhotoService,
    private val dao: PhotoDao
) : IBasePhotoRepository {

    override suspend fun insertPhoto(photo: List<PhotoDatabaseItem>) {
        Timber.tag("photoRepository").d("PhotoDatabaseRepository.savePhotoIntoDatabase")
        dao.insert(photo)

    }

    // Repository to separate Dao & viewModel
    override suspend fun getPhotoFromDb(): List<PhotoDatabaseItem> {
        Timber.tag("getPhotoFromDb").d("getPhotoFromDb()")
        return dao.queryPhotoList()
    }

    override suspend fun getPhotoFromApi(): Resource<List<PhotoRespItem>> {

        return performPhotoOperation(
            networkCall = {
                apiService.getImg()
            }
        )
    }

}