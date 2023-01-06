package com.example.aslcodingtestproject.displayphotos.data.local.dao

import androidx.room.*
import com.example.aslcodingtestproject.displayphotos.data.entities.PhotoDatabaseItem

// Data Access Object, as app need to save record to local database for offline
@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photoList: List<PhotoDatabaseItem>)

    @Query("SELECT * FROM PhotoDatabaseItem ORDER BY title ASC")
    fun queryPhotoList(): List<PhotoDatabaseItem>

}