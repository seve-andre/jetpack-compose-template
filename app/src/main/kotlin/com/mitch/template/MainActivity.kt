package com.mitch.template

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.template.domain.models.TemplateThemeConfig
import com.mitch.template.ui.designsystem.TemplateTheme
import com.mitch.template.ui.designsystem.components.snackbars.TemplateSnackbarHost
import com.mitch.template.ui.designsystem.components.snackbars.toVisuals
import com.mitch.template.ui.designsystem.theme.custom.LocalPadding
import com.mitch.template.ui.designsystem.theme.custom.padding
import com.mitch.template.ui.navigation.TemplateDestination
import com.mitch.template.ui.navigation.TemplateNavHost
import com.mitch.template.ui.rememberTemplateAppState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /* Must be called before super.onCreate()
         *
         * Splashscreen look in res/values/themes.xml
         */
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val dependenciesProvider = (application as TemplateApplication).dependenciesProvider
        val viewModel = MainActivityViewModel(dependenciesProvider.userSettingsRepository)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        setContent {
            val isThemeDark = shouldUseDarkTheme(uiState)
            UpdateSystemBarsEffect(isThemeDark)

            CompositionLocalProvider(LocalPadding provides padding) {
                TemplateTheme(isThemeDark = isThemeDark) {
                    val appState = rememberTemplateAppState(
                        networkMonitor = dependenciesProvider.networkMonitor
                    )

                    Scaffold(
                        snackbarHost = { TemplateSnackbarHost(appState.snackbarHostState) },
                        contentWindowInsets = WindowInsets(0)
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
                            TemplateNavHost(
                                onShowSnackbar = { event ->
                                    appState
                                        .snackbarHostState
                                        .showSnackbar(event.toVisuals())
                                },
                                dependenciesProvider = dependenciesProvider,
                                navController = appState.navController,
                                startDestination = TemplateDestination.Screen.Home
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun UpdateSystemBarsEffect(isThemeDark: Boolean) {
        DisposableEffect(isThemeDark) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT
                ) { isThemeDark },
                navigationBarStyle = SystemBarStyle.auto(
                    LightScrim,
                    DarkScrim
                ) { isThemeDark }
            )
            onDispose { }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.theme) {
        TemplateThemeConfig.Dark -> true
        TemplateThemeConfig.Light -> false
        TemplateThemeConfig.FollowSystem -> isSystemInDarkTheme()
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val LightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val DarkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
