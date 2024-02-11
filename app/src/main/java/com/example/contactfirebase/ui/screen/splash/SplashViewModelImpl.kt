package com.example.contactfirebase.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactfirebase.navigator.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val navigator: AppNavigator
) : ViewModel(), SplashViewModel {

    override fun openContactScreen() {
        viewModelScope.launch {
            navigator.navigationTo(SplashScreenDirections.actionSplashScreenToContactScreen())
        }

    }

}