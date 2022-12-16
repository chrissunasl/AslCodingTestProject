package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import retrofit2.Response

class FakePhotoRepository: BasePhotoRepository {

    private var photos : ArrayList<GetPhotoRespItem> = ArrayList()

    val getPhoto: MutableLiveData<ArrayList<GetPhotoRespItem>> = MutableLiveData(photos)

    val dbPhoto : LiveData<MutableList<GetPhotoRespItem>> = MutableLiveData(photos)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    fun refreshLiveData(){
        getPhoto.postValue(photos)
    }

    override suspend fun insertPhoto(photoList: ArrayList<GetPhotoRespItem>){
        photos = photoList
        refreshLiveData()
    }

    override fun getPhotoFromDb(): LiveData<MutableList<GetPhotoRespItem>> {
        return dbPhoto
    }

    override suspend fun getPhotoFromApi(): Resource<ArrayList<GetPhotoRespItem>>{
        return if(shouldReturnNetworkError){
            Resource.error("Error", null)
        }else{
            val photoList : ArrayList<GetPhotoRespItem> = ArrayList()
            val photoItem1 = GetPhotoRespItem(
                1, 1,
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952")

            photoList.add(photoItem1)
            Resource.success(photoList)
        }
    }

}