package com.example.contactfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.contactfirebase.domain.AppRepositoryImpl
import com.example.contactfirebase.navigator.AppNavigatorHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appNavigatorHandler: AppNavigatorHandler
    @Inject
    lateinit var repositoryImpl: AppRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = hostFragment.navController

        appNavigatorHandler.buffer
            .onEach { it.invoke(navController) }
            .launchIn(lifecycleScope)

    }
}