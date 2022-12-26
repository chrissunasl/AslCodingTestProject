package com.example.aslcodingtestproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.example.aslcodingtestproject.model.repository.BasePhotoDetailRepository
import com.example.aslcodingtestproject.model.repository.BasePhotoRepository
import com.example.aslcodingtestproject.view.event.OnLoadingEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

// Photo Related viewModel
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: BasePhotoRepository,
    private val photoDetailRepository: BasePhotoDetailRepository
) : ViewModel() {

    val photos: LiveData<ArrayList<GetPhotoRespItem>?> get() = _photos
    private val _photos = MutableLiveData<ArrayList<GetPhotoRespItem>?>()

    val photoComments: LiveData<ArrayList<GetPhotoDetailRespItem>?> get() = _photoComments
    private val _photoComments = MutableLiveData<ArrayList<GetPhotoDetailRespItem>?>()

    // photo handle
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun savePhotoIntoDatabase(photo: PhotoDatabaseItem) = viewModelScope.launch {
        GlobalScope.launch { photoRepository.insertPhoto(photo) }.join()
    }

    fun getPhotoFromDb() = photoRepository.getPhotoFromDb()

    fun getPhotoFromApi(onLoadingListener: OnLoadingEventListener) = viewModelScope.launch {
        when (photoRepository.getPhotoFromApi().status) {
            Resource.Status.LOADING -> {
                onLoadingListener.startLoading()
            }
            Resource.Status.SUCCESS -> {
                val resp = photoRepository.getPhotoFromApi().data
                resp?.sortWith { p1, p2 -> p1.title.compareTo(p2.title) }
                _photos.postValue(resp)
                onLoadingListener.stopLoading()
                //save data to database
                resp?.forEach { item ->
                    savePhotoIntoDatabase(
                        PhotoDatabaseItem(
                            albumId = item.albumId,
                            id = item.id,
                            thumbnailUrl = item.thumbnailUrl,
                            title = item.title,
                            url = item.url
                        )
                    )
                }
            }
            Resource.Status.ERROR -> {
                // if error/no response, use database
                _photos.postValue(ArrayList())
                onLoadingListener.stopLoading()
            }
        }
    }

    fun getPhotoDetailFromApi(id: String, onLoadingListener: OnLoadingEventListener) =
        viewModelScope.launch {
            when (photoDetailRepository.getPhotoDetailFromApi(id).status) {
                Resource.Status.LOADING -> {
                    onLoadingListener.startLoading()
                }
                Resource.Status.SUCCESS -> {
                    _photoComments.postValue(photoDetailRepository.getPhotoDetailFromApi(id).data)
                    onLoadingListener.stopLoading()
                }
                Resource.Status.ERROR -> {
                    _photoComments.postValue(ArrayList())
                    onLoadingListener.stopLoading()
                }
            }
        }

    // return the first item position of photo list
    fun findPositionByTitle(
        keyword: String,
        dataList: MutableList<GetPhotoRespItem>
    ): Int {
        val patter = keyword.uppercase(Locale.getDefault()).toRegex()
        val filteredList = dataList.filter {
            patter.containsMatchIn(it.title.uppercase(Locale.getDefault()))
        }
        return if (filteredList.isNotEmpty()) {
            dataList.indexOf(filteredList[0])
        } else {
            0
        }
    }

}