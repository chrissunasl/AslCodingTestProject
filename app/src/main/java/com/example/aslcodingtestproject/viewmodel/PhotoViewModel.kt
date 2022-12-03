package com.example.aslcodingtestproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*

import com.example.aslcodingtestproject.model.database.structure.PhotoBase
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.performNonTokenGetOperation
import com.example.aslcodingtestproject.model.remote.requestobj.BaseRequest
import com.example.aslcodingtestproject.model.remote.responseobj.BaseResponse
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoResp
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespX
import com.example.aslcodingtestproject.model.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,

    ) : ViewModel() {

    // Data for observing
    var photo = MutableLiveData<GetPhotoRespX?>()

    suspend fun getPhoto() {
        Timber.i("getPhoto")
        val start = Calendar.getInstance()

//        CoroutineScope(Dispatchers.IO).launch {
//            val response = photoRepository.getPhoto()
//            withContext(Dispatchers.Main) {
//                photo.postValue(response.body())
//            }
//        }.invokeOnCompletion {
//            Timber.i("getPhoto().end, cost: ${(Calendar.getInstance().timeInMillis - start.timeInMillis)} ms")
//        }

        try {
            val response = photoRepository.getPhoto()
            photo.postValue(response.body())
            Log.d("chris","photoRepository.getPhoto(): $photo")
        }catch (e: Exception){
            photo.postValue(null)
            Log.e("chris","e: $e")
        }
    }
}