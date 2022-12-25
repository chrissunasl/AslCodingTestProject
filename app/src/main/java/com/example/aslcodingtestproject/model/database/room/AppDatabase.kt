package com.example.aslcodingtestproject.model.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem

// Room Library config
// Connecting Dao
@Database(
    entities = [
        GetPhotoRespItem::class,
    ],
    version = 16,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private const val DB_NAME = "ASLCodingTest"

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
                DB_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}