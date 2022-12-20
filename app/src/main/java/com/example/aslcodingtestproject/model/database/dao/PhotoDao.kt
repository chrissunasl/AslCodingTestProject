package com.example.aslcodingtestproject.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// Data Access Object, as app need to save record to local database for offline
@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(getPhotoRespItem: ArrayList<GetPhotoRespItem>)

    @Query("SELECT * FROM GetPhotoRespItem ORDER BY title ASC")
    fun queryPhotoList(): LiveData<MutableList<GetPhotoRespItem>>

    @Query("DELETE FROM GetPhotoRespItem")
    fun deleteAll(): Int

}