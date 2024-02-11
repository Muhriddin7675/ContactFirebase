package com.example.contactfirebase.di

import com.example.contactfirebase.navigator.AppNavigation
import com.example.contactfirebase.navigator.AppNavigationDispatcher
import com.example.contactfirebase.navigator.AppNavigator
import com.example.contactfirebase.navigator.AppNavigatorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppNavigatorModule {

    @Binds
    fun appNavigator(impl: AppNavigationDispatcher): AppNavigator

    @Binds
    fun appNavigatorHandler(impl: AppNavigationDispatcher): AppNavigatorHandler
}