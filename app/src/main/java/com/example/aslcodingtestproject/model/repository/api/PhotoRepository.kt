package com.example.aslcodingtestproject.model.repository.api

import androidx.lifecycle.LiveData
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performPhotoOperation
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.remote.service.PhotoService
import com.example.aslcodingtestproject.model.repository.BasePhotoRepository
import timber.log.Timber
import javax.inject.Inject

// get data
class PhotoRepository @Inject constructor(
    private val apiService: PhotoService,
    private val dao: PhotoDao
): BasePhotoRepository {

    override suspend fun insertPhoto(photo: PhotoDatabaseItem) {
            Timber.tag("photoRepository").d("PhotoDatabaseRepository.savePhotoIntoDatabase $photo")
            dao.insert(photo)

    }

    // Repository to separate Dao & viewModel
    override fun getPhotoFromDb():  LiveData<MutableList<PhotoDatabaseItem>> {
        return dao.queryPhotoList()
    }

    override suspend fun getPhotoFromApi():Resource<ArrayList<GetPhotoRespItem>>{



        return performPhotoOperation(
            networkCall = {

                apiService.getImg()
            }
        )
    }

}