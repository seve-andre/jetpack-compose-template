package com.mitch.appname

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.appname.navigation.NavGraphs
import com.mitch.appname.ui.theme.AppMaterialTheme
import com.mitch.appname.ui.util.rememberAppState
import com.mitch.appname.util.AppTheme
import com.mitch.appname.util.network.NetworkMonitor
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // if not needed, also remove permission from AndroidManifest.xml
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        /* Must be called before super.onCreate()
         *
         * Splashscreen look in res/values/themes.xml
         */
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isThemeDark = shouldUseDarkTheme(uiState)

            AppMaterialTheme(
                isThemeDark = isThemeDark
            ) {
                val appState = rememberAppState(networkMonitor)
                val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                LaunchedEffect(isOffline) {
                    if (isOffline) {
                        appState.snackbarHostState.showSnackbar(
                            message = "not connected!",
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(appState.snackbarHostState) },
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .consumeWindowInsets(padding)
                            .windowInsetsPadding(
                                WindowInsets.safeDrawing.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
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

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.theme) {
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }
}
