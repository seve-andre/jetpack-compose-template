package com.mitch.template.ui.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.mitch.template.ui.designsystem.theme.custom.DarkExtendedColorScheme
import com.mitch.template.ui.designsystem.theme.custom.LightExtendedColorScheme
import com.mitch.template.ui.designsystem.theme.custom.LocalExtendedColorScheme
import com.mitch.template.ui.designsystem.theme.custom.LocalPadding
import com.mitch.template.ui.designsystem.theme.custom.padding

val DarkColorScheme: ColorScheme = darkColorScheme()
val LightColorScheme: ColorScheme = lightColorScheme()

typealias TemplateDesignSystem = MaterialTheme

@Composable
fun TemplateTheme(
    isThemeDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isThemeDark) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val extendedColorScheme = if (isThemeDark) {
        DarkExtendedColorScheme
    } else {
        LightExtendedColorScheme
    }

    CompositionLocalProvider(
        LocalExtendedColorScheme provides extendedColorScheme,
        LocalPadding provides TemplateDesignSystem.padding
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
