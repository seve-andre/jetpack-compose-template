package com.mitch.appname.ui.designsystem.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalPadding = compositionLocalOf { Padding() }
val padding: Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current

data class Padding(
    val zero: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)
