package com.example.aslcodingtestproject.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aslcodingtestproject.model.database.structure.PhotoBase

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vehicleClasses: List<PhotoBase>)

    @Query("SELECT * FROM PhotoBase")
    fun queryClassList(): LiveData<MutableList<PhotoBase>>


    @Query("DELETE FROM PhotoBase")
    fun deleteAll(): Int

    @Transaction
    suspend fun deleteAndInsertAll(dataList: List<PhotoBase>?) {
        deleteAll()
        if (!dataList.isNullOrEmpty())
            insert(dataList)
    }
}