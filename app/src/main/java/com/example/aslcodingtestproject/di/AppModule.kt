package com.example.aslcodingtestproject.di

import android.content.Context
import com.example.aslcodingtestproject.common.IConstants
import com.example.aslcodingtestproject.common.converter.EnumConverterFactory
import com.example.aslcodingtestproject.data.local.dao.PhotoDao
import com.example.aslcodingtestproject.data.local.room.AppDatabase
import com.example.aslcodingtestproject.common.interceptor.MyOkHttpClient
import com.example.aslcodingtestproject.data.remote.api.PhotoService
import com.example.aslcodingtestproject.domain.repository.IBasePhotoCommentRepository
import com.example.aslcodingtestproject.domain.repository.IBasePhotoRepository
import com.example.aslcodingtestproject.data.repository.PhotoCommentRepository
import com.example.aslcodingtestproject.data.repository.PhotoRepository
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * AppModule Injection
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Build Retrofit
    @Singleton
    @Provides
    @Named("PhotoRetrofit")
    fun providePhotoRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(IConstants.DomainName.API_DOMAIN_JSONPLACEHOLDER) // set request url
        .client(MyOkHttpClient.getOkHttpClient())// add okhttp client for no rewriting web service code
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .disableHtmlEscaping()
                    .create()
            )
        ) // add response data convert factory for analytical data
        .addConverterFactory(EnumConverterFactory())
        .build()


    // Use retrofit
    @Singleton
    @Provides
    fun getPhotoService(@Named("PhotoRetrofit") retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)

    // Use Database
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun PhotoDao(db: AppDatabase) = db.getPhotoDao()

    // Definition of dependency injection binding
    @Singleton
    @Provides
    fun providePhotoRepository(apiService: PhotoService,
                               dao: PhotoDao
    ) = PhotoRepository(apiService, dao) as IBasePhotoRepository

    @Singleton
    @Provides
    fun providePhotoCommentRepository(apiService: PhotoService
    ) = PhotoCommentRepository(apiService) as IBasePhotoCommentRepository

    @Singleton
    @Provides
    fun provideDispatchersProvider() : DispatchersProvider {
        return DispatchersImpl()
    }
}