package com.example.fetch.di

import com.example.fetch.repository.FetchRepository
import com.example.fetch.repository.FetchRepositoryImpl
import com.example.fetch.usecase.FetchUseCase
import com.example.fetch.usecase.FetchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindFetchUseCase(
        fetchUseCaseImpl: FetchUseCaseImpl
    ): FetchUseCase

    @Binds
    abstract fun bindFetchRepository(
        fetchRepositoryImpl: FetchRepositoryImpl
    ): FetchRepository
}
