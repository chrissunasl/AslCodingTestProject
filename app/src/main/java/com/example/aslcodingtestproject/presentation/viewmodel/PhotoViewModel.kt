package com.example.aslcodingtestproject.presentation.viewmodel

import androidx.lifecycle.*
import com.example.aslcodingtestproject.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.domain.repository.IBasePhotoCommentRepository
import com.example.aslcodingtestproject.domain.repository.IBasePhotoRepository
import com.example.aslcodingtestproject.presentation.photocomment.PhotoCommentItem
import com.example.aslcodingtestproject.presentation.photos.PhotoItem
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
    private val photoRepository: IBasePhotoRepository,
    private val photoCommentRepository: IBasePhotoCommentRepository
) : ViewModel() {

    val photos: LiveData<List<PhotoItem>?> get() = _photos
    private val _photos = MutableLiveData<List<PhotoItem>?>()

    val photoComments: LiveData<List<PhotoCommentItem>?> get() = _photoComments
    private val _photoComments = MutableLiveData<List<PhotoCommentItem>?>()

    val status: LiveData<Resource.Status> get() = _status
    private val _status = MutableLiveData<Resource.Status>()

    // photo handle
    @OptIn(DelicateCoroutinesApi::class)
    fun savePhotoIntoDatabase(photo: List<PhotoDatabaseItem>) = viewModelScope.launch {
        GlobalScope.launch { photoRepository.insertPhoto(photo) }.join()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun observePhotoFromDb() {
        // launch a coroutine to run in background thread
        GlobalScope.launch {
            val photosDatabase = photoRepository.getPhotoFromDb()
            Timber.i("observePhotoFromDb() $photosDatabase")
            if (photosDatabase.isNotEmpty()) {
                // sort and map to view display object
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
                // sort and map to view display object
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

    fun getPhotoCommentFromApi(id: String) =
        viewModelScope.launch {
            val resp = photoCommentRepository.getPhotoCommentFromApi(id)
            when (resp.status) {
                Resource.Status.LOADING -> {
                    _status.postValue(resp.status)
                }
                Resource.Status.SUCCESS -> {
                    if (!resp.data.isNullOrEmpty()) {
                        _photoComments.postValue(resp.data.map {
                            PhotoCommentItem(
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

    // return the first item position of photo list, not returning list
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