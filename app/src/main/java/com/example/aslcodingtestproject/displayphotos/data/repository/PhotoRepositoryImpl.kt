package com.example.aslcodingtestproject.displayphotos.data.repository

import com.example.aslcodingtestproject.common.Resource
import com.example.aslcodingtestproject.common.mapper.photoDatabaseItemListToPhotoItemList
import com.example.aslcodingtestproject.common.mapper.photoRespItemListToPhotoDatabaseItemList
import com.example.aslcodingtestproject.common.mapper.photoRespItemListToPhotoItemList
import com.example.aslcodingtestproject.displayphotos.data.entities.PhotoDatabaseItem
import com.example.aslcodingtestproject.displayphotos.data.local.dao.PhotoDao
import com.example.aslcodingtestproject.displayphotos.data.performPhotoOperation
import com.example.aslcodingtestproject.displayphotos.data.remote.api.PhotoService
import com.example.aslcodingtestproject.displayphotos.domain.repository.IPhotoRepository
import com.example.aslcodingtestproject.displayphotos.presentation.photos.PhotoItem
import timber.log.Timber
import javax.inject.Inject

// get data
class PhotoRepositoryImpl @Inject constructor(
    private val apiService: PhotoService,
    private val dao: PhotoDao
) : IPhotoRepository {

    override suspend fun insertPhoto(photo: List<PhotoDatabaseItem>) {
        Timber.tag("photoRepository").d("PhotoDatabaseRepository.savePhotoIntoDatabase")
        dao.insert(photo)

    }

    // Repository to separate Dao & viewModel
    override suspend fun getPhotoFromDb(): List<PhotoDatabaseItem> {
        Timber.tag("getPhotoFromDb").d("getPhotoFromDb()")
        return dao.queryPhotoList()
    }

    override suspend fun getPhotos(): Resource<List<PhotoItem>> {
        val res = performPhotoOperation(
            networkCall = {
                apiService.getImg()
            },
            callResult = {
                if (!it.isNullOrEmpty()) {
                    insertPhoto(photoRespItemListToPhotoDatabaseItemList(it))
                }
            }
        )

        return if(res.data != null){
            Resource.success(photoRespItemListToPhotoItemList(res.data).sortedBy { it.title })
        }else{
            val photosDatabase = getPhotoFromDb()
            Timber.i("observePhotoFromDb() $photosDatabase")
            if (photosDatabase.isNotEmpty()) {
                // sort and map to view display object
                photosDatabase.sortedBy { it.title }
                Resource.success(photoDatabaseItemListToPhotoItemList(photosDatabase).sortedBy { it.title })
            }else{
                Resource.error("Error", null)
            }
        }

    }

}