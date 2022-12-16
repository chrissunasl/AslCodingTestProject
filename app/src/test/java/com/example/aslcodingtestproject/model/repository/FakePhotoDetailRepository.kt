package com.example.aslcodingtestproject.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aslcodingtestproject.model.remote.Resource
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import retrofit2.Response

class FakePhotoDetailRepository: BasePhotoDetailRepository {

    private var photos : ArrayList<GetPhotoDetailRespItem> = ArrayList()

    val getPhoto: MutableLiveData<ArrayList<GetPhotoDetailRespItem>> = MutableLiveData(photos)

    val dbPhoto : LiveData<MutableList<GetPhotoDetailRespItem>> = MutableLiveData(photos)


    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    fun refreshLiveData(){
        getPhoto.postValue(photos)
    }

    override suspend fun insertPhotoDetail(photoList: ArrayList<GetPhotoDetailRespItem>){
        photos = photoList
        refreshLiveData()
    }

    override fun getPhotoDetailFromDb(): LiveData<MutableList<GetPhotoDetailRespItem>> {
        return dbPhoto
    }

    override suspend fun getPhotoDetailFromApi(id: String): Resource<ArrayList<GetPhotoDetailRespItem>>{
        return if(shouldReturnNetworkError){
            Resource.error("Error", null)
        }else{
            val photoList : ArrayList<GetPhotoDetailRespItem> = ArrayList()
            val photoItem1 = GetPhotoDetailRespItem(
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                1,
                "https://via.placeholder.com/150/92c952",1,
            )

            photoList.add(photoItem1)
            Resource.success(photoList)
        }
    }

}