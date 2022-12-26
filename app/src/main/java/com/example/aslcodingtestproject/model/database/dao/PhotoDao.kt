package com.example.aslcodingtestproject.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem

// Data Access Object, as app need to save record to local database for offline
@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(getPhotoRespItem: PhotoDatabaseItem)

    @Query("SELECT * FROM PhotoDatabaseItem ORDER BY title ASC")
    fun queryPhotoList(): LiveData<MutableList<PhotoDatabaseItem>>

    @Query("DELETE FROM PhotoDatabaseItem")
    fun deleteAll(): Int
}