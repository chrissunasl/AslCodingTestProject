package com.example.aslcodingtestproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.common.dispatchers.DispatchersProvider
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

    fun refreshPhotoList() = viewModelScope.launch(dispatchersProvider.io) {
        val resp = photoRepository.getPhotos()
        when (resp.status) {
            Resource.Status.LOADING -> {
                _status.postValue(resp.status)
            }
            Resource.Status.SUCCESS -> {
                if (!resp.data.isNullOrEmpty()) {
                    _photos.postValue(resp.data)
                }
                _status.postValue(resp.status)
            }
            Resource.Status.ERROR -> {
                _status.postValue(resp.status)
            }
        }
    }

    fun getPhotoComments(id: String) =
        viewModelScope.launch(dispatchersProvider.default) {
            val resp = photoCommentRepository.getPhotoComments(id)
            when (resp.status) {
                Resource.Status.LOADING -> {
                    _status.postValue(resp.status)
                }
                Resource.Status.SUCCESS -> {
                    if (!resp.data.isNullOrEmpty()) {
                        _photoComments.postValue(resp.data)
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
        dataList: List<PhotoItem>?
    ): Int {
        val patter = keyword.uppercase(Locale.getDefault()).toRegex()
        return if (dataList != null) {
            val filteredList = dataList.filter {
                patter.containsMatchIn(it.title.uppercase(Locale.getDefault()))
            }
            try {
                dataList.indexOf(filteredList[0])
            } catch (e: Exception) {
                Timber.e(e.message)
                0
            }
        } else {
            0
        }
    }


}