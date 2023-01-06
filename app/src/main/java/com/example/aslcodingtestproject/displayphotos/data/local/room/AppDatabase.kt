package com.example.aslcodingtestproject.displayphotos.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aslcodingtestproject.displayphotos.data.local.dao.PhotoDao
import com.example.aslcodingtestproject.displayphotos.data.entities.PhotoDatabaseItem

// Room Library config
// Connecting Dao
@Database(
    entities = [
        PhotoDatabaseItem::class,
    ],
    version = 28,
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

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}