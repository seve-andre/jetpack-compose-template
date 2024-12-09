package com.mitch.template

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.template.domain.models.TemplateThemePreference
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
            val themeInfo = themeInfo(uiState)
            DisposableEffect(themeInfo.isThemeDark, themeInfo.shouldFollowSystem) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT
                    ) { themeInfo.isThemeDark },
                    navigationBarStyle = SystemBarStyle.auto(
                        LightScrim,
                        DarkScrim
                    ) { themeInfo.isThemeDark },
                )
                setAppTheme(
                    uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager,
                    isThemeDark = themeInfo.isThemeDark,
                    shouldFollowSystem = themeInfo.shouldFollowSystem
                )
                onDispose { }
            }

            CompositionLocalProvider(LocalPadding provides padding) {
                TemplateTheme(isThemeDark = themeInfo.isThemeDark) {
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

    // Workaround to let Compose know that the configuration has changed
    // https://issuetracker.google.com/issues/321896385
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val composeView = window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as? ComposeView
        composeView?.dispatchConfigurationChanged(newConfig)
    }
}

private data class ThemeInfo(val isThemeDark: Boolean, val shouldFollowSystem: Boolean)

@Composable
private fun themeInfo(uiState: MainActivityUiState): ThemeInfo {
    return when (uiState) {
        MainActivityUiState.Loading -> ThemeInfo(
            isThemeDark = isSystemInDarkTheme(),
            shouldFollowSystem = false
        )

        is MainActivityUiState.Success -> {
            val isThemeDark = uiState.theme == TemplateThemePreference.Dark
            val shouldFollowSystem = uiState.theme == TemplateThemePreference.FollowSystem
            ThemeInfo(
                isThemeDark = isThemeDark || (shouldFollowSystem && isSystemInDarkTheme()),
                shouldFollowSystem = shouldFollowSystem
            )
        }
    }
}

/**
 * Sets app theme to reflect user choice.
 */
private fun setAppTheme(
    uiModeManager: UiModeManager,
    isThemeDark: Boolean,
    shouldFollowSystem: Boolean
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        uiModeManager.setApplicationNightMode(
            if (shouldFollowSystem) {
                UiModeManager.MODE_NIGHT_AUTO
            } else if (isThemeDark) {
                UiModeManager.MODE_NIGHT_YES
            } else {
                UiModeManager.MODE_NIGHT_NO
            }
        )
    } else {
        AppCompatDelegate.setDefaultNightMode(
            if (shouldFollowSystem) {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            } else if (isThemeDark) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
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
