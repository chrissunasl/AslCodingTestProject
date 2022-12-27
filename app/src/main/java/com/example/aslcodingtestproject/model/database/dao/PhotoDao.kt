package com.example.aslcodingtestproject.model.database.dao

import androidx.room.*
import com.example.aslcodingtestproject.model.database.dataclassobject.PhotoDatabaseItem

// Data Access Object, as app need to save record to local database for offline
@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(getPhotoRespItem: List<PhotoDatabaseItem>)

    @Query("SELECT * FROM PhotoDatabaseItem ORDER BY title ASC")
    fun queryPhotoList(): List<PhotoDatabaseItem>

    @Query("DELETE FROM PhotoDatabaseItem")
    fun deleteAll(): Int
}