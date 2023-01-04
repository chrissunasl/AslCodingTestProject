package com.example.aslcodingtestproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.di.DispatchersProvider
import com.example.aslcodingtestproject.domain.repository.IBasePhotoCommentRepository
import com.example.aslcodingtestproject.domain.repository.IBasePhotoRepository
import com.example.aslcodingtestproject.presentation.photocomment.PhotoCommentItem
import com.example.aslcodingtestproject.presentation.photos.PhotoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

// Photo Related viewModel
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: IBasePhotoRepository,
    private val photoCommentRepository: IBasePhotoCommentRepository,
    private val dispatchersProvider: DispatchersProvider // Already inject in AppModule and replace hardcoding dispatcher
) : ViewModel() {

    val photos: LiveData<List<PhotoItem>?> get() = _photos
    private val _photos = MutableLiveData<List<PhotoItem>?>()


    val photoComments: LiveData<List<PhotoCommentItem>?> get() = _photoComments
    private val _photoComments = MutableLiveData<List<PhotoCommentItem>?>()

    val status: LiveData<Resource.Status> get() = _status
    private val _status = MutableLiveData<Resource.Status>()

    // photo handle
    private fun savePhotoIntoDatabase(photo: List<PhotoDatabaseItem>) = viewModelScope.launch(dispatchersProvider.io) {
        photoRepository.insertPhoto(photo)
    }

    private fun getPhotoFromDb() = viewModelScope.launch(dispatchersProvider.io) {
        // launch a coroutine to run in background thread

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

    fun getPhotoFromApi() = viewModelScope.launch(dispatchersProvider.io) {
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
                    getPhotoFromDb()
                }
            }
            Resource.Status.ERROR -> {
                _status.postValue(resp.status)
                getPhotoFromDb()
            }
        }
    }

    fun getPhotoCommentFromApi(id: String) =
        viewModelScope.launch(dispatchersProvider.default) {
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