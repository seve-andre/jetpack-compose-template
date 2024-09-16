package com.mitch.template

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.mitch.template.core.designsystem.TemplateTheme
import com.mitch.template.core.designsystem.components.snackbars.DismissibleSnackbar
import com.mitch.template.core.designsystem.custom.LocalPadding
import com.mitch.template.core.designsystem.custom.padding
import com.mitch.template.core.domain.NetworkMonitor
import com.mitch.template.core.domain.models.TemplateThemeConfig
import com.mitch.template.core.ui.SnackbarManager
import com.mitch.template.core.designsystem.components.snackbars.toVisuals
import com.mitch.template.ui.rememberTemplateAppState
import com.mitch.template.navigation.TemplateDestination
import com.mitch.template.navigation.TemplateNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // if not needed, also remove permission from AndroidManifest.xml
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        /* Must be called before super.onCreate()
         *
         * Splashscreen look in res/values/themes.xml
         */
        val splashScreen = installSplashScreen()

        // use the entire display to draw
        enableEdgeToEdge()
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

        setContent {
            val isThemeDark = shouldUseDarkTheme(uiState)
            UpdateSystemBarsEffect(isThemeDark)

            CompositionLocalProvider(LocalPadding provides padding) {
                TemplateTheme(isThemeDark = isThemeDark) {
                    val appState = rememberTemplateAppState(networkMonitor)
                    val snackbarHostState = appState.snackbarHostState
                    // val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                    // observe snackbars
                    // (they persist across navigation; if this behavior is not desired, see below)
                    val lifecycleOwner = LocalLifecycleOwner.current
                    LaunchedEffect(lifecycleOwner.lifecycle, SnackbarManager.events) {
                        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            withContext(Dispatchers.Main.immediate) {
                                SnackbarManager.events.collect { data ->
                                    // uncomment if new snackbar should dismiss old one
                                    // snackbarHostState.currentSnackbarData?.dismiss()

                                    val result = snackbarHostState.showSnackbar(data.toVisuals())
                                    when (result) {
                                        SnackbarResult.Dismissed -> data.onDismiss?.invoke()
                                        SnackbarResult.ActionPerformed -> data.action?.onPerformAction?.invoke()
                                    }
                                }
                            }
                        }
                    }

                    // to dismiss snackbar on navigation changed
                    DisposableEffect(appState.navController) {
                        val listener = NavController.OnDestinationChangedListener { _, _, _ ->
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }
                        appState.navController.addOnDestinationChangedListener(listener)

                        onDispose {
                            appState.navController.removeOnDestinationChangedListener(listener)
                        }
                    }

                    Scaffold(
                        snackbarHost = { DismissibleSnackbar(appState.snackbarHostState) },
                        contentWindowInsets = WindowInsets(0)
                    ) { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                                .consumeWindowInsets(padding)
                                .windowInsetsPadding(
                                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                                )
                        ) {
                            TemplateNavHost(
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
