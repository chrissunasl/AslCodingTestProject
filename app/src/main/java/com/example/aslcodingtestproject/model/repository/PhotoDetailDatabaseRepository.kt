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
class PhotoDetailDatabaseRepository @Inject constructor(
    private val detailDao: PhotoDetailDao
) {

    fun savePhotoDetailIntoDatabase(photoList: ArrayList<GetPhotoDetailRespItem>) {
        if(photoList.isNotEmpty()){
            Log.d("chris", "PhotoDetailDatabaseRepository.savePhotoIntoDatabase $photoList")
            detailDao.insertPhotoDetail(photoList)
        }
    }

    fun getPhotoDetailFromDb() = detailDao.queryPhotoDetailList()



}