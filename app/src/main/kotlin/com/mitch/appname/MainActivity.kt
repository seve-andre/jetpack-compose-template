package com.mitch.appname

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.appname.ui.NavGraphs
import com.mitch.appname.ui.theme.AppTheme
import com.mitch.appname.ui.util.rememberAppState
import com.mitch.appname.util.network.NetworkMonitor
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @OptIn(
        ExperimentalMaterial3Api::class,
        ExperimentalLifecycleComposeApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appState = rememberAppState(networkMonitor)
                    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                    LaunchedEffect(isOffline) {
                        if (isOffline) {
                            appState.snackbarHostState.showSnackbar(
                                message = "not connected!",
                                duration = SnackbarDuration.Indefinite,
                            )
                        }
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(appState.snackbarHostState) }
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = appState.navController
                            )
                        }
                    }
                }
            }
        }
    }
}
