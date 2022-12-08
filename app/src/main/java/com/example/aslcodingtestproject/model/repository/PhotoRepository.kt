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
class PhotoRepository @Inject constructor(
    private val apiService: NonTokenService,
    private val dao: PhotoDao,
    private val detailDao: PhotoDetailDao
) {



    fun getPhotoFromDb() = dao.queryPhotoList()

    fun getPhotoFromApi(): LiveData<Resource<ArrayList<GetPhotoRespItem>>> {
        val nowDateTime = DateTimeFormatter.ofPattern(
            IConstants.BASIC.AsymmetricKeyDateTimeFormat
        ).format(ZonedDateTime.now())

        //val dataList = apiService.getImg(nowDateTime)

        return performNonTokenGetOperation(
            networkCall = {
                apiService.getImg(nowDateTime)
            },
            getCallResult = {

                if (it != null) {
                    Log.d("chris", "getPhotoFromApi(), $it")
                    dao.insert(it)
                }
            }

        )
    }




    fun getPhotoDetailFromDb() = detailDao.queryPhotoDetailList()

    fun getPhotoDetailFromApi(id: String): LiveData<Resource<ArrayList<GetPhotoDetailRespItem>>> {
        val nowDateTime = DateTimeFormatter.ofPattern(
            IConstants.BASIC.AsymmetricKeyDateTimeFormat
        ).format(ZonedDateTime.now())

        //val dataList = apiService.getImg(nowDateTime)

        return performNonTokenGetOperation(
            networkCall = {
                apiService.getImgDetail(nowDateTime, id = id)
            },
            getCallResult = {

                if (it != null) {
                    detailDao.insertPhotoDetail(it)
                }
            }

        )
    }



}