package com.example.aslcodingtestproject.model.dependencyinjection

import android.content.Context
import com.example.aslcodingtestproject.constant.IConstants
import com.example.aslcodingtestproject.converter.EnumConverterFactory
import com.example.aslcodingtestproject.model.database.dao.PhotoDao
import com.example.aslcodingtestproject.model.database.room.AppDatabase
import com.example.aslcodingtestproject.model.remote.interceptor.MyOkHttpClient
import com.example.aslcodingtestproject.model.remote.service.PhotoService
import com.example.aslcodingtestproject.model.repository.BasePhotoDetailRepository
import com.example.aslcodingtestproject.model.repository.BasePhotoRepository
import com.example.aslcodingtestproject.model.repository.api.PhotoDetailRepository
import com.example.aslcodingtestproject.model.repository.api.PhotoRepository
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
 * Retrofit
 * Separate to different types of retrofit
 * Eg: Need Token
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*  Okhttp non Token  */
    // Build Retrofit
    @Singleton
    @Provides
    @Named("PhotoRetrofit")
    fun providePhotoRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(IConstants.DomainName.API_DOMAIN_JSONPLACEHOLDER)
        .client(MyOkHttpClient.getOkHttpClient(null))
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .disableHtmlEscaping()
                    .create()
            )
        )
        .addConverterFactory(EnumConverterFactory())
        .build()

    // Use retrofit
    @Singleton
    @Provides
    fun getPhotoService(@Named("PhotoRetrofit") retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)

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
    ) = PhotoRepository(apiService, dao) as BasePhotoRepository

    @Singleton
    @Provides
    fun providePhotoDetailRepository(apiService: PhotoService
    ) = PhotoDetailRepository(apiService) as BasePhotoDetailRepository

}