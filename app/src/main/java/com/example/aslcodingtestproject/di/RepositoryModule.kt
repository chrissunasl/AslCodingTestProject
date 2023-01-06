package com.example.aslcodingtestproject.di

import com.example.aslcodingtestproject.displayphotos.data.repository.PhotoCommentRepositoryImpl
import com.example.aslcodingtestproject.displayphotos.data.repository.PhotoRepositoryImpl
import com.example.aslcodingtestproject.displayphotos.domain.repository.IPhotoCommentRepository
import com.example.aslcodingtestproject.displayphotos.domain.repository.IPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Definition of dependency injection binding
    // Binds replace Provide: single param & less code
    @Binds
    @Singleton
    abstract fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl) : IPhotoRepository

    @Binds
    @Singleton
    abstract fun bindPhotoCommentRepository(photoCommentRepositoryImpl: PhotoCommentRepositoryImpl) : IPhotoCommentRepository

}