package com.example.aslcodingtestproject.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.database.dao.PhotoDetailDao
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performNonTokenGetOperation
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import javax.inject.Inject

// get data
class PhotoDatabaseRepository @Inject constructor(
    private val dao: PhotoDao,
) {

    fun savePhotoIntoDatabase(photoList: ArrayList<GetPhotoRespItem>) {
        if(photoList.isNotEmpty()){
            Log.d("chris", "PhotoDatabaseRepository.savePhotoIntoDatabase $photoList")
            dao.insert(photoList)
        }
    }

    // Repository to separate Dao & viewModel
    fun getPhotoFromDb() = dao.queryPhotoList()

}