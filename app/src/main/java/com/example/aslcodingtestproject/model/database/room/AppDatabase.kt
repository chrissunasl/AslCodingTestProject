package com.example.aslcodingtestproject.model.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aslcodingtestproject.model.converter.DateConverter
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.database.structure.PhotoBase


@Database(
    entities = [
        PhotoBase::class,
    ],
    version = 13,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getVehicleDao(): PhotoDao

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

//            val passphrase: ByteArray = SQLiteDatabase.getBytes("userEnteredPassphrase".toCharArray())
//            val factory = SupportFactory(passphrase)

            return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                DB_Name
            ).fallbackToDestructiveMigration()
                .addTypeConverter(DateConverter())
//                .openHelperFactory(factory)
                .build()

        }
    }

}