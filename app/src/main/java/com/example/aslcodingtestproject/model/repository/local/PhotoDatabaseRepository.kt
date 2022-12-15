package com.example.aslcodingtestproject.model.repository.local

import android.util.Log
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
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