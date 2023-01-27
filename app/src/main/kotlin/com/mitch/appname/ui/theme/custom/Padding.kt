package com.mitch.appname.ui.theme.custom

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val padding = Padding()

data class Padding(
    val zero: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)
