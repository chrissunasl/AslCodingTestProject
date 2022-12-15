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
    private val detailDao: PhotoDetailDao
): BasePhotoRepository {

    override fun getPhotoFromApi():LiveData<Resource<ArrayList<GetPhotoRespItem>>>{
        return performNonTokenGetOperation(
            networkCall = {
                val nowDateTime = DateTimeFormatter.ofPattern(
                    IConstants.BASIC.AsymmetricKeyDateTimeFormat
                ).format(ZonedDateTime.now())
                apiService.getImg(nowDateTime)
            }
        )
    }

    override fun getPhotoDetailFromDb() = detailDao.queryPhotoDetailList()

    override fun getPhotoDetailFromApi(id: String): LiveData<Resource<ArrayList<GetPhotoDetailRespItem>>> {

        return performNonTokenGetOperation(
            networkCall = {
                val nowDateTime = DateTimeFormatter.ofPattern(
                    IConstants.BASIC.AsymmetricKeyDateTimeFormat
                ).format(ZonedDateTime.now())
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