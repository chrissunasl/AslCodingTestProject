package com.example.aslcodingtestproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.repository.api.PhotoDetailRepository
import com.example.aslcodingtestproject.model.repository.api.PhotoRepository
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

// Photo Related viewModel
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val photoDetailRepository: PhotoDetailRepository
) : ViewModel() {

    val photo: MutableLiveData<ArrayList<GetPhotoRespItem>?> = MutableLiveData()
    val photoComment:  MutableLiveData<ArrayList<GetPhotoDetailRespItem>?> = MutableLiveData()

    // photo handle
    @OptIn(DelicateCoroutinesApi::class)
    fun savePhotoIntoDatabase(photoList: ArrayList<GetPhotoRespItem>) = viewModelScope.launch{
        GlobalScope.launch { photoRepository.insertPhoto(photoList) }.join()
    }

    fun getPhotoFromDb() = photoRepository.getPhotoFromDb()

    fun getPhotoFromApi(onLoadingListener: OnLoadingEventListener)  = viewModelScope.launch{
        when(photoRepository.getPhotoFromApi().status){
            Resource.Status.LOADING -> {onLoadingListener.startLoading()}
            Resource.Status.SUCCESS -> {
                photo.postValue(photoRepository.getPhotoFromApi().data)
                onLoadingListener.stopLoading()
            }
            Resource.Status.ERROR -> {
                photo.postValue(ArrayList())
                onLoadingListener.stopLoading()
            }
        }
    }

    fun getPhotoDetailFromApi(id: String, onLoadingListener: OnLoadingEventListener) = viewModelScope.launch {
        when(photoDetailRepository.getPhotoDetailFromApi(id).status){
            Resource.Status.LOADING -> {onLoadingListener.startLoading()}
            Resource.Status.SUCCESS -> {
                photoComment.postValue(photoDetailRepository.getPhotoDetailFromApi(id).data)
                onLoadingListener.stopLoading()
            }
            Resource.Status.ERROR -> {
                photo.postValue(ArrayList())
                onLoadingListener.stopLoading()
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
            if (patter.containsMatchIn(data.title.uppercase(Locale.getDefault()))) {
                resultList.add(data)
            }
        }
        return resultList
    }
}