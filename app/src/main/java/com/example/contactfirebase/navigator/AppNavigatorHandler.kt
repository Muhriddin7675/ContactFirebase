package com.example.contactfirebase.navigator

import kotlinx.coroutines.flow.Flow

interface AppNavigatorHandler {
    val buffer: Flow<AppNavigation>
}