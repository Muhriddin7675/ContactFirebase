package com.example.contactfirebase.navigator

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigationDispatcher @Inject constructor() : AppNavigator, AppNavigatorHandler {

    override val buffer = MutableSharedFlow<AppNavigation>()

    private suspend fun navigate(navigation: AppNavigation) {
        buffer.emit(navigation)
    }

    override suspend fun navigationTo(directions: NavDirections) = navigate {
        navigate(directions)
    }

    override suspend fun popBack() = navigate {
        popBackStack()
    }
}

