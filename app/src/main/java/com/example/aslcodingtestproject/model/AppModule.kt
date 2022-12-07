package com.autotoll.ffts.di.module

import android.content.Context
import com.autotoll.ffts.model.constant.IConstants
import com.example.aslcodingtestproject.converter.EnumConverterFactory
import com.example.aslcodingtestproject.model.database.dao.PhotoDetailDao
import com.example.aslcodingtestproject.model.database.room.AppDatabase
import com.example.aslcodingtestproject.model.remote.interceptor.MyOkHttpClient
import com.example.aslcodingtestproject.model.remote.service.NonTokenService
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
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*  Okhttp non Token  */
    @Singleton
    @Provides
    @Named("NonTokenRetrofit")
    fun provideNonTokenRetrofit(
        @ApplicationContext appContext: Context,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(IConstants.DomainName.API_DOMAIN_JSONPLACEHOLDER)
        .client(MyOkHttpClient.getOkHttpClient(appContext, null, isNeedApiAccessToken = false))
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .disableHtmlEscaping()
                    .create()
            )
        )
        .addConverterFactory(EnumConverterFactory())
        .build()



    // Service Before Login
    @Singleton
    @Provides
    fun getNonTokenService(@Named("NonTokenRetrofit") retrofit: Retrofit): NonTokenService =
        retrofit.create(NonTokenService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun PhotoDao(db: AppDatabase) = db.getPhotoDao()

    @Singleton
    @Provides
    fun PhotoDetailDao(db: AppDatabase) = db.getPhotoDetailDao()
}