package com.mitch.appname.ui.designsystem.theme.custom

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColorScheme(
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color
)

val LightExtendedColorScheme = ExtendedColorScheme(
    success = Color(0xFF395F27),
    onSuccess = Color(0xFFFDFCFF),
    warning = Color(0xFFE1DE33),
    onWarning = Color(0xFF424242)
)

val DarkExtendedColorScheme = ExtendedColorScheme(
    success = Color(0xFF4EBD26),
    onSuccess = Color(0xFF1A1C1E),
    warning = Color(0xFFDAD741),
    onWarning = Color(0xFF1A1C1E)
)

val LocalExtendedColorScheme = staticCompositionLocalOf { LightExtendedColorScheme }
val MaterialTheme.extendedColorScheme: ExtendedColorScheme
    @Composable
    get() = LocalExtendedColorScheme.current
