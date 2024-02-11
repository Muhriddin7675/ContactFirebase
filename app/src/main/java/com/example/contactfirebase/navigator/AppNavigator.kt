package com.example.contactfirebase.navigator

import androidx.navigation.NavDirections

interface AppNavigator {
    suspend fun navigationTo(directions: NavDirections)
    suspend fun popBack()
}