package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.MainApplication
import com.example.aslcodingtestproject.model.database.structure.PhotoBase
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performNonTokenGetOperation
import com.example.aslcodingtestproject.model.remote.requestobj.BaseRequest
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoResp
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespX
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import timber.log.Timber
import java.security.interfaces.RSAPublicKey
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: NonTokenService
) {

//    fun getPhoto(): GetPhotoResp? = performNonTokenGetOperation(
//        val nowDateTime = DateTimeFormatter.ofPattern(
//            IConstants.BASIC.AsymmetricKeyDateTimeFormat
//        ).format(ZonedDateTime.now())
//        try {
//
//            val response = apiService.getImg(nowDateTime).execute()
//
//
//            Timber.i("getRetryAsymmetricKey(), ${response}")
//
//            return response
//        } catch (e: Exception) {
//            // If any encryption Error
//            return null
//        }
//
//        )

    suspend fun getPhoto(): Response<GetPhotoRespX> {
        val nowDateTime = DateTimeFormatter.ofPattern(
            IConstants.BASIC.AsymmetricKeyDateTimeFormat
        ).format(ZonedDateTime.now())

        return apiService.getImg(nowDateTime)

    }

}