package com.example.aslcodingtestproject.viewmodel

import androidx.lifecycle.*
import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.repository.BasePhotoDetailRepository
import com.example.aslcodingtestproject.model.repository.BasePhotoRepository
import com.example.aslcodingtestproject.view.viewdata.PhotoDetailItem
import com.example.aslcodingtestproject.view.viewdata.PhotoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

// Photo Related viewModel
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: BasePhotoRepository,
    private val photoDetailRepository: BasePhotoDetailRepository
) : ViewModel() {

    val photos: LiveData<List<PhotoItem>?> get() = _photos
    private val _photos = MutableLiveData<List<PhotoItem>?>()

    val photoComments: LiveData<List<PhotoDetailItem>?> get() = _photoComments
    private val _photoComments = MutableLiveData<List<PhotoDetailItem>?>()

    val status: LiveData<Resource.Status> get() = _status
    private val _status = MutableLiveData<Resource.Status>()

    // photo handle
    @OptIn(DelicateCoroutinesApi::class)
    fun savePhotoIntoDatabase(photo: List<PhotoDatabaseItem>) = viewModelScope.launch {
        GlobalScope.launch { photoRepository.insertPhoto(photo) }.join()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun observePhotoFromDb() {
        GlobalScope.launch {
            val photosDatabase = photoRepository.getPhotoFromDb()
            Timber.i("observePhotoFromDb() $photosDatabase")
            if (photosDatabase.isNotEmpty()) {
                photosDatabase.sortedBy { it.title }
                _photos.postValue(photosDatabase.map {
                    PhotoItem(
                        id = it.id,
                        thumbnailUrl = it.thumbnailUrl,
                        title = it.title,
                        url = it.url
                    )
                })
            }
        }
    }

    fun getPhotoFromApi() = viewModelScope.launch {
        val resp = photoRepository.getPhotoFromApi()
        when (resp.status) {
            Resource.Status.LOADING -> {
                _status.postValue(resp.status)

            }
            Resource.Status.SUCCESS -> {

                val respData = resp.data?.sortedBy { it.title }

                if (!respData.isNullOrEmpty()) {

                    _photos.postValue(respData.map {
                        PhotoItem(
                            id = it.id,
                            thumbnailUrl = it.thumbnailUrl,
                            title = it.title,
                            url = it.url
                        )
                    })
                    _status.postValue(resp.status)
                    savePhotoIntoDatabase(
                        respData.map {
                            PhotoDatabaseItem(
                                albumId = it.albumId,
                                id = it.id,
                                thumbnailUrl = it.thumbnailUrl,
                                title = it.title,
                                url = it.url
                            )
                        }
                    )

                } else {
                    _status.postValue(resp.status)
                    observePhotoFromDb()
                }
            }
            Resource.Status.ERROR -> {
                _status.postValue(resp.status)
                observePhotoFromDb()
            }
        }
    }

    fun getPhotoDetailFromApi(id: String) =
        viewModelScope.launch {
            val resp = photoDetailRepository.getPhotoDetailFromApi(id)
            when (resp.status) {
                Resource.Status.LOADING -> {
                    _status.postValue(resp.status)
                }
                Resource.Status.SUCCESS -> {
                    if (!resp.data.isNullOrEmpty()) {
                        _photoComments.postValue(resp.data.map {
                            PhotoDetailItem(
                                body = it.body,
                                id = it.id
                            )
                        })
                    }
                    _status.postValue(resp.status)
                }
                Resource.Status.ERROR -> {
                    _status.postValue(resp.status)
                }
            }
        }

    // return the first item position of photo list
    fun findPositionByTitle(
        keyword: String,
        dataList: List<PhotoItem>
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