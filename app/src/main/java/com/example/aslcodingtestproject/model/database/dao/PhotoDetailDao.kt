package com.example.aslcodingtestproject.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem

// Data Access Object, as app need to save record to local database for offline
@Dao
interface PhotoDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotoDetail(getPhotoRespItem: ArrayList<GetPhotoDetailRespItem>)

    @Query("SELECT * FROM GetPhotoDetailRespItem ORDER BY id ASC LIMIT 20  ")
    fun queryPhotoDetailList(): LiveData<MutableList<GetPhotoDetailRespItem>>

    @Query("DELETE FROM GetPhotoDetailRespItem")
    fun deleteAllPhotoDetail(): Int

}