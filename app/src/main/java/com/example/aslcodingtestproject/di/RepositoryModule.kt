package com.example.aslcodingtestproject.di

import com.example.aslcodingtestproject.data.repository.PhotoCommentRepository
import com.example.aslcodingtestproject.data.repository.PhotoRepository
import com.example.aslcodingtestproject.domain.repository.IBasePhotoCommentRepository
import com.example.aslcodingtestproject.domain.repository.IBasePhotoRepository
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
    abstract fun bindPhotoRepository(photoRepository: PhotoRepository) : IBasePhotoRepository

    @Binds
    @Singleton
    abstract fun bindPhotoCommentRepository(photoCommentRepository: PhotoCommentRepository) : IBasePhotoCommentRepository

}