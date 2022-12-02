package com.example.aslcodingtestproject.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*

import com.example.aslcodingtestproject.model.database.structure.PhotoBase
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.requestobj.BaseRequest
import com.example.aslcodingtestproject.model.remote.responseobj.BaseResponse
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoResp
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
    var photo = MutableLiveData<GetPhotoResp?>()

    fun getPhoto(viewLifecycleOwner: LifecycleOwner) {

        val start = Calendar.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            async {
                photoRepository.getPhoto().observe(viewLifecycleOwner){
                    when (it.status) {
                        Resource.Status.LOADING -> {}
                        Resource.Status.SUCCESS -> {
                            val resp = it.data
                            photo.postValue(resp)
                        }
                        else -> {
                            photo.postValue(null)
                        }
                    }
                }
            }

        }.invokeOnCompletion {
            Timber.i("getPhoto complete, cost: ${(Calendar.getInstance().timeInMillis - start.timeInMillis)} ms")

        }

    }
}