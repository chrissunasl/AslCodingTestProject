package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// interface
interface BasePhotoRepository {

    fun getPhotoFromApi():  LiveData<Resource<ArrayList<GetPhotoRespItem>>>

    fun getPhotoDetailFromDb(): LiveData<MutableList<GetPhotoDetailRespItem>>

    fun getPhotoDetailFromApi(id: String): LiveData<Resource<ArrayList<GetPhotoDetailRespItem>>>

}