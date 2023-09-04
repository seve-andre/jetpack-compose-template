package com.mitch.appname.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val DarkColorScheme = darkColorScheme()
val LightColorScheme = lightColorScheme()

@Composable
fun AppMaterialTheme(
    isThemeDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isThemeDark) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    /**
     * Use this if [Navigation bar](https://m3.material.io/components/navigation-bar/overview) (with tonal elevation) is used
     */
    // val navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(NavigationBarDefaults.Elevation)

    val systemUiController = rememberSystemUiController()
    // Update the dark content of the system bars to match the theme
    DisposableEffect(systemUiController, isThemeDark) {
        systemUiController.setSystemBarsColor(
            color = colorScheme.background,
            darkIcons = !isThemeDark
        )
        onDispose {}
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}
