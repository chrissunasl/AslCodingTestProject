package com.example.aslcodingtestproject.viewmodel

import android.util.Log
import androidx.lifecycle.*

import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.repository.local.PhotoDatabaseRepository
import com.example.aslcodingtestproject.model.repository.PhotoRepository
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

// Photo Related viewModel
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val photoDatabaseRepository: PhotoDatabaseRepository
) : ViewModel() {

    val photo: MutableLiveData<ArrayList<GetPhotoRespItem>?> = MutableLiveData()

    fun savePhotoIntoDatabase(photoList: ArrayList<GetPhotoRespItem>) {
        photoDatabaseRepository.savePhotoIntoDatabase(photoList)
    }

    fun getPhotoFromDb() = photoDatabaseRepository.getPhotoFromDb()

    fun getPhotoFromApi(
        viewLifecycleOwner: LifecycleOwner,
        onLoadingListener: OnLoadingEventListener,
    ) {
        photoRepository.getPhotoFromApi()
            .observe(viewLifecycleOwner) {

                when (it.status) {
                    Resource.Status.LOADING -> {
                        onLoadingListener.startLoading()
                    }
                    Resource.Status.SUCCESS -> {
                        Log.d("chris", "GetPhotoRespItem: ${it.data}")

//                        if(!it.data.isNullOrEmpty()){
//                            photoRepository.savePhotoIntoDatabase(it.data)
//                        }
                        photo.postValue(it.data)
                        onLoadingListener.stopLoading()
                    }

                    Resource.Status.ERROR -> {
                        photo.postValue(ArrayList())
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
                    Resource.Status.LOADING -> {
                        onLoadingListener.startLoading()
                    }
                    Resource.Status.SUCCESS -> {

                        onLoadingListener.stopLoading()
                    }

                    Resource.Status.ERROR -> {
                        onLoadingListener.stopLoading()
                    }
                }
            }
    }

    fun search(
        keyword: String,
        dataList: MutableList<GetPhotoRespItem>
    ): ArrayList<GetPhotoRespItem> {
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