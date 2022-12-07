package com.example.aslcodingtestproject.viewmodel

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.*

import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespX
import com.example.aslcodingtestproject.model.repository.PhotoRepository
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

// Photo Related viewModel
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    ) : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private var _photos = MutableLiveData<List<GetPhotoRespItem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    var photos: MutableLiveData<ArrayList<GetPhotoRespItem>> = MutableLiveData()

    fun getPhotoFromDb() = photoRepository.getPhotoFromDb()


    fun getPhotoFromApi(
        viewLifecycleOwner: LifecycleOwner,
        onLoadingListener: OnLoadingEventListener,
    ) {
        photoRepository.getPhotoFromApi()
            .observe(viewLifecycleOwner) {

                when (it.status) {
                    Resource.Status.LOADING -> {onLoadingListener.startLoading()}
                    Resource.Status.SUCCESS -> {
                        Log.d("chris", "GetPhotoRespItem: ${it.data}")
                        onLoadingListener.stopLoading()
                    }

                    Resource.Status.ERROR -> {
                        onLoadingListener.stopLoading()
                    }
                }
            }
    }

    fun getPhotoDetailFromDb() = photoRepository.getPhotoDetailFromDb()

    fun getPhotoDetailFromApi(
        viewLifecycleOwner: LifecycleOwner,
        onLoadingListener: OnLoadingEventListener,
        id: String
    ) {
        photoRepository.getPhotoDetailFromApi(id)
            .observe(viewLifecycleOwner) {

                when (it.status) {
                    Resource.Status.LOADING -> {onLoadingListener.startLoading()}
                    Resource.Status.SUCCESS -> {

                        onLoadingListener.stopLoading()
                    }

                    Resource.Status.ERROR -> {
                        onLoadingListener.stopLoading()
                    }
                }
            }
    }

    fun search(keyword: String, dataList: MutableList<GetPhotoRespItem>): ArrayList<GetPhotoRespItem> {
        val resultList = ArrayList<GetPhotoRespItem>()
        val patter = keyword.uppercase(Locale.getDefault()).toRegex()
        dataList.forEach { data ->
            if (patter.containsMatchIn(data.title.uppercase(Locale.getDefault()).toString())) {
                resultList.add(data)
            }
        }
        return resultList
    }
}