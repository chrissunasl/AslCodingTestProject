package com.example.aslcodingtestproject.model.repository.local

import android.util.Log
import com.example.aslcodingtestproject.model.database.dao.PhotoDetailDao
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
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