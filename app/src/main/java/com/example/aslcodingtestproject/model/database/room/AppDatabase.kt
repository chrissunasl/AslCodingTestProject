package com.example.aslcodingtestproject.model.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.database.dao.PhotoDetailDao
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoDetailRespItem
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// Room Library config
// Connecting Dao

@Database(
    entities = [
        GetPhotoRespItem::class,
        GetPhotoDetailRespItem::class
    ],
    version = 14,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao
    abstract fun getPhotoDetailDao(): PhotoDetailDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private const val DB_Name = "ASLCodingTest"

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }


        private fun buildDatabase(appContext: Context): AppDatabase {

            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                DB_Name
            ).fallbackToDestructiveMigration()
                .build()

        }
    }

}