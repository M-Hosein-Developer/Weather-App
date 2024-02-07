package com.example.weatherapp.di

import com.example.weatherapp.model.repository.HomeRepository
import com.example.weatherapp.model.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRepository(repositoryImpl: HomeRepositoryImpl) : HomeRepository

}