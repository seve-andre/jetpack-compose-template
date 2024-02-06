package com.mitch.appname

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitch.appname.domain.models.AppTheme
import com.mitch.appname.navigation.NavGraphs
import com.mitch.appname.ui.designsystem.AppMaterialTheme
import com.mitch.appname.ui.designsystem.components.snackbars.AppSnackbar
import com.mitch.appname.ui.designsystem.components.snackbars.AppSnackbarDefaults
import com.mitch.appname.ui.designsystem.components.snackbars.AppSnackbarType
import com.mitch.appname.ui.designsystem.components.snackbars.AppSnackbarVisuals
import com.mitch.appname.ui.designsystem.theme.custom.LocalPadding
import com.mitch.appname.ui.designsystem.theme.custom.padding
import com.mitch.appname.ui.rememberAppState
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

    @OptIn(ExperimentalMaterial3Api::class)
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
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            val isThemeDark = shouldUseDarkTheme(uiState)
            val shouldFollowSystem = shouldFollowSystemTheme(uiState)

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(isThemeDark) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { isThemeDark },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { isThemeDark },
                )
                setAppTheme(
                    uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager,
                    isThemeDark = isThemeDark,
                    shouldFollowSystem = shouldFollowSystem
                )
                onDispose { }
            }

            CompositionLocalProvider(LocalPadding provides padding) {
                AppMaterialTheme(
                    isThemeDark = isThemeDark
                ) {
                    val appState = rememberAppState(networkMonitor)
                    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

                    LaunchedEffect(isOffline) {
                        if (isOffline) {
                            appState.snackbarHostState.showSnackbar(
                                message = getString(R.string.not_connected),
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    }

                    val dismissSnackbarState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value != SwipeToDismissBoxValue.Settled) {
                                appState.snackbarHostState.currentSnackbarData?.dismiss()
                                true
                            } else {
                                false
                            }
                        }
                    )

                    LaunchedEffect(dismissSnackbarState.currentValue) {
                        if (dismissSnackbarState.currentValue != SwipeToDismissBoxValue.Settled) {
                            dismissSnackbarState.reset()
                        }
                    }

                    Scaffold(
                        snackbarHost = {
                            SwipeToDismissBox(
                                state = dismissSnackbarState,
                                backgroundContent = { },
                                content = {
                                    SnackbarHost(
                                        hostState = appState.snackbarHostState,
                                        modifier = Modifier
                                            .navigationBarsPadding()
                                            .imePadding()
                                            .padding(horizontal = padding.medium)
                                    ) { snackbarData ->
                                        val customVisuals =
                                            snackbarData.visuals as AppSnackbarVisuals

                                        val colors = when (customVisuals.type) {
                                            AppSnackbarType.Default -> AppSnackbarDefaults.defaultSnackbarColors()
                                            AppSnackbarType.Success -> AppSnackbarDefaults.successSnackbarColors()
                                            AppSnackbarType.Warning -> AppSnackbarDefaults.warningSnackbarColors()
                                            AppSnackbarType.Error -> AppSnackbarDefaults.errorSnackbarColors()
                                        }

                                        AppSnackbar(
                                            colors = colors,
                                            icon = customVisuals.imageVector,
                                            message = customVisuals.message,
                                            action = customVisuals.actionLabel?.let {
                                                {
                                                    TextButton(
                                                        onClick = snackbarData::performAction,
                                                        colors = ButtonDefaults.textButtonColors(
                                                            contentColor = colors.actionColor
                                                        )
                                                    ) {
                                                        Text(text = customVisuals.actionLabel)
                                                    }
                                                }
                                            },
                                            dismissAction = if (customVisuals.duration == SnackbarDuration.Indefinite) {
                                                {
                                                    IconButton(
                                                        onClick = snackbarData::dismiss,
                                                        colors = IconButtonDefaults.iconButtonColors(
                                                            contentColor = MaterialTheme.colorScheme.inverseOnSurface
                                                        )
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = stringResource(
                                                                id = R.string.dismiss_snackbar_message
                                                            )
                                                        )
                                                    }
                                                }
                                            } else {
                                                null
                                            }
                                        )
                                    }
                                }
                            )
                        },
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
                            val onShowSnackbar: suspend (AppSnackbarVisuals) -> SnackbarResult = {
                                appState.snackbarHostState.showSnackbar(it)
                            }

                            DestinationsNavHost(
                                navGraph = NavGraphs.root,
                                navController = appState.navController,

                                // to provide snackbar lambda to invoke in "@Composable"s
                                /*dependenciesContainerBuilder = {
                                    dependency(HomeRouteDestination) { onShowSnackbar }
                                }*/
                            )
                        }
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
        AppTheme.Dark -> true
        AppTheme.Light -> false
        AppTheme.FollowSystem -> isSystemInDarkTheme()
    }
}

@Composable
private fun shouldFollowSystemTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.theme) {
        AppTheme.FollowSystem -> isSystemInDarkTheme()
        else -> false
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
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
