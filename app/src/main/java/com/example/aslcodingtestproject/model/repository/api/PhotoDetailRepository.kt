package com.example.aslcodingtestproject.model.repository.api

import androidx.lifecycle.LiveData
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.model.database.dao.PhotoDetailDao
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performNonTokenNormalGetOperation
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
import com.example.aslcodingtestproject.model.repository.BasePhotoDetailRepository
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// get data
class PhotoDetailRepository @Inject constructor(
    private val apiService: NonTokenService,
    private val dao: PhotoDetailDao
): BasePhotoDetailRepository {

    override suspend fun insertPhotoDetail(photoDetailList: ArrayList<GetPhotoDetailRespItem>) {
        dao.insertPhotoDetail(photoDetailList)
    }

    override fun getPhotoDetailFromDb(): LiveData<MutableList<GetPhotoDetailRespItem>> {
        return dao.queryPhotoDetailList()
    }

    override suspend fun getPhotoDetailFromApi(id: String): Resource<ArrayList<GetPhotoDetailRespItem>> {
        return performNonTokenNormalGetOperation(
            networkCall = {
                val nowDateTime = DateTimeFormatter.ofPattern(
                    IConstants.BASIC.AsymmetricKeyDateTimeFormat
                ).format(ZonedDateTime.now())
                apiService.getImgDetail(nowDateTime, id = id)
            },
        )
    }

}