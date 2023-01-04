package com.example.aslcodingtestproject.data.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.data.local.dao.PhotoDao
import com.example.aslcodingtestproject.data.remote.api.PhotoService
import com.example.aslcodingtestproject.data.remote.resp.PhotoRespItem
import com.example.aslcodingtestproject.domain.repository.IBasePhotoRepository
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val TAG = "PhotoRepository"

// get data
class PhotoRepository @Inject constructor(
    private val apiService: PhotoService,
    private val dao: PhotoDao
) : IBasePhotoRepository {

    override suspend fun insertPhoto(photo: List<PhotoDatabaseItem>) {
        Timber.tag("photoRepository").d("PhotoDatabaseRepository.savePhotoIntoDatabase")
        dao.insert(photo)

    }

    // Repository to separate Dao & viewModel
    override suspend fun getPhotoFromDb(): List<PhotoDatabaseItem> {
        Timber.tag("getPhotoFromDb").d("getPhotoFromDb()")
        return dao.queryPhotoList()
    }

    override suspend fun getPhotoFromApi(): Resource<List<PhotoRespItem>> {
        return try {
            val responseData = apiService.getImg()
            if (responseData.isSuccessful && responseData.body() != null){
                Resource.success(responseData.body())
            }else{
                Resource.error("not successful", null)
            }
        } catch (e: IOException) {
            Timber.tag(TAG).d("IOException, check internet connection")
            Resource.error("IOException, check internet connection", null)
        } catch (e: HttpException) {
            Timber.tag(TAG).d("HttpException, unexpected response")
            Resource.error("HttpException, unexpected response", null)
        }
    }

}