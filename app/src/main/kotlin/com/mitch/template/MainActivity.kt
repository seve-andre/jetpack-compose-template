package com.mitch.template

import android.app.UiModeManager
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.ui.designsystem.TemplateTheme
import com.mitch.template.ui.designsystem.components.snackbars.TemplateSnackbarHost
import com.mitch.template.ui.designsystem.components.snackbars.toVisuals
import com.mitch.template.ui.navigation.TemplateDestination
import com.mitch.template.ui.navigation.TemplateNavHost
import com.mitch.template.ui.rememberTemplateAppState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Force the 3-button navigation bar to be transparent
            // See: https://developer.android.com/develop/ui/views/layout/edge-to-edge#create-transparent
            window.isNavigationBarContrastEnforced = false
        }
        val dependenciesProvider = (application as TemplateApplication).dependenciesProvider
        super.onCreate(savedInstanceState)

        val viewModel = MainActivityViewModel(dependenciesProvider.userSettingsRepository)
        var themeInfo by mutableStateOf(
            ThemeInfo(
                isThemeDark = resources.configuration.isSystemInDarkTheme,
                shouldFollowSystem = false
            )
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    dependenciesProvider.userSettingsRepository.initLocaleIfNeeded()
                }

                launch {
                    combine(
                        isSystemInDarkTheme(),
                        viewModel.uiState
                    ) { isSystemInDarkTheme, uiState ->
                        themeInfo(isSystemInDarkTheme = isSystemInDarkTheme, uiState = uiState)
                    }
                        .onEach { themeInfo = it }
                        .distinctUntilChanged()
                        .collect { themeInfo ->
                            enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.auto(
                                    Color.TRANSPARENT,
                                    Color.TRANSPARENT
                                ) { themeInfo.isThemeDark },
                                navigationBarStyle = SystemBarStyle.auto(
                                    Color.TRANSPARENT,
                                    Color.TRANSPARENT
                                ) { themeInfo.isThemeDark }
                            )
                            setAppTheme(
                                uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager,
                                isThemeDark = themeInfo.isThemeDark,
                                shouldFollowSystem = themeInfo.shouldFollowSystem
                            )
                        }
                }
            }
        }

        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value is MainActivityUiState.Loading
        }

        setContent {
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

private fun themeInfo(
    isSystemInDarkTheme: Boolean,
    uiState: MainActivityUiState
): ThemeInfo {
    return when (uiState) {
        MainActivityUiState.Loading -> ThemeInfo(
            isThemeDark = isSystemInDarkTheme,
            shouldFollowSystem = false
        )

        is MainActivityUiState.Success -> {
            val isThemeDark = uiState.theme == TemplateThemePreference.Dark
            val shouldFollowSystem = uiState.theme == TemplateThemePreference.FollowSystem
            ThemeInfo(
                isThemeDark = isThemeDark || (shouldFollowSystem && isSystemInDarkTheme),
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
        val mode = when {
            shouldFollowSystem -> UiModeManager.MODE_NIGHT_AUTO
            isThemeDark -> UiModeManager.MODE_NIGHT_YES
            else -> UiModeManager.MODE_NIGHT_NO
        }
        uiModeManager.setApplicationNightMode(mode)
    } else {
        val mode = when {
            shouldFollowSystem -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            isThemeDark -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}

/**
 * Convenience wrapper for dark mode checking
 */
private val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

/**
 * Registers listener for configuration changes to retrieve whether system is in dark theme or not.
 * Immediately upon subscribing, it sends the current value and then registers listener for changes.
 */
private fun AppCompatActivity.isSystemInDarkTheme() = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)

    val listener = Consumer<Configuration> {
        channel.trySend(it.isSystemInDarkTheme)
    }

    addOnConfigurationChangedListener(listener)

    awaitClose { removeOnConfigurationChangedListener(listener) }
}
    .distinctUntilChanged()
    .conflate()
