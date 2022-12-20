package com.example.aslcodingtestproject.model.repository.api

import android.util.Log
import androidx.lifecycle.LiveData
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performNonTokenNormalGetOperation
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
import com.example.aslcodingtestproject.model.repository.BasePhotoRepository
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// get data
class PhotoRepository @Inject constructor(
    private val apiService: NonTokenService,
    private val dao: PhotoDao
): BasePhotoRepository {

    override suspend fun insertPhoto(photoList: ArrayList<GetPhotoRespItem>) {
        if(photoList.isNotEmpty()){
            Log.d("chris", "PhotoDatabaseRepository.savePhotoIntoDatabase $photoList")
            dao.insert(photoList)
        }
    }

    // Repository to separate Dao & viewModel
    override fun getPhotoFromDb():  LiveData<MutableList<GetPhotoRespItem>> {
        return dao.queryPhotoList()
    }

    override suspend fun getPhotoFromApi():Resource<ArrayList<GetPhotoRespItem>>{
        return performNonTokenNormalGetOperation(
            networkCall = {
                val nowDateTime = DateTimeFormatter.ofPattern(
                    IConstants.BASIC.AsymmetricKeyDateTimeFormat
                ).format(ZonedDateTime.now())
                apiService.getImg(nowDateTime)
            }
        )
    }

}