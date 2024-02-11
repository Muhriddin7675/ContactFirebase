package com.example.contactfirebase.di

import com.example.contactfirebase.domain.AppRepository
import com.example.contactfirebase.domain.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppRepositoryModule {

    @Binds
    fun getRepository(impl: AppRepositoryImpl):AppRepository
}