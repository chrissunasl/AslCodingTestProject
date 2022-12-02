package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.model.database.structure.PhotoBase
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performNonTokenGetOperation
import com.example.aslcodingtestproject.model.remote.requestobj.BaseRequest
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoResp
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import java.security.interfaces.RSAPublicKey
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val apiService: NonTokenService
) {

    fun getPhoto(): LiveData<Resource<GetPhotoResp>> = liveData(Dispatchers.IO) {
        // Current Request Date Time

        val time = ZonedDateTime.now()

        val nowDateTime = DateTimeFormatter.ofPattern(
            IConstants.BASIC.AsymmetricKeyDateTimeFormat
        )
            .format(time)  //In case that server time is not the same with client time

        // Execute Request
        var response: Response<*>? = null
        try {
            response = apiService.getImg(nowDateTime)

            if (response.isSuccessful && response.body()?.result == true) {

                val data = response.body()
                // Save data into Shared Preference

                emit(Resource.success(data))
                return@liveData
            }

            emit(Resource.error( "", null))

        } catch (e: Exception) {
            // If any encryption Error
            emit(Resource.error("Error", null))
        }
    }

}