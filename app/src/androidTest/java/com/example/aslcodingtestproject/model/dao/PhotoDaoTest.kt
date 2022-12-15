package com.example.aslcodingtestproject.model.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.aslcodingtestproject.getOrAwaitValue
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.database.room.AppDatabase
import com.example.aslcodingtestproject.model.remote.responseobj.GetPhotoRespItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PhotoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var  database: AppDatabase
    private lateinit var dao: PhotoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getPhotoDao()

    }

    @After
    fun teardown(){
        database.close()
    }

    // runBlockingTest if suspend
    @Test
    fun insertPhotoItem() = runBlockingTest {
        val photoList : ArrayList<GetPhotoRespItem> = ArrayList()
        val photoItem1 = GetPhotoRespItem(
            1, 1,
        "accusamus beatae ad facilis cum similique qui sunt",
        "https://via.placeholder.com/600/92c952",
        "https://via.placeholder.com/150/92c952")

        photoList.add(photoItem1)

        dao.insert(photoList)

        val allPhoto = dao.queryPhotoList().getOrAwaitValue()

        assertThat(allPhoto).contains(photoItem1)

    }

    @Test
    fun deleteShoppingItem(){
        val photoList : ArrayList<GetPhotoRespItem> = ArrayList()
        val photoItem1 = GetPhotoRespItem(
            1, 1,
            "accusamus beatae ad facilis cum similique qui sunt",
            "https://via.placeholder.com/600/92c952",
            "https://via.placeholder.com/150/92c952")

        photoList.add(photoItem1)

        dao.insert(photoList)
        dao.deleteAll()

        val allPhoto = dao.queryPhotoList().getOrAwaitValue()

        assertThat(allPhoto).doesNotContain(photoItem1)
    }

}