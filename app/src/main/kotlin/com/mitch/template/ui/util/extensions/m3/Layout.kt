package com.mitch.template.ui.util.extensions.m3

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mitch.template.ui.designsystem.theme.custom.LocalPadding

// padding

// https://m3.material.io/foundations/layout/applying-layout/compact#4b2b6814-c64a-4bc0-a07d-6652a91737e6
val WindowWidthSizeClass.CompactPadding: PaddingValues
    @Composable
    @ReadOnlyComposable
    get() = PaddingValues(LocalPadding.current.medium)

// https://m3.material.io/foundations/layout/applying-layout/medium#4899a0c6-bc71-4e86-8095-39e5d517db6a
val WindowWidthSizeClass.MediumPadding: PaddingValues
    get() = PaddingValues(24.dp)

// https://m3.material.io/foundations/layout/applying-layout/expanded#3f62eeac-33c3-4639-8d06-0cb091d8e7f5
val WindowWidthSizeClass.ExpandedPadding: PaddingValues
    get() = PaddingValues(24.dp)

// width - https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes
val WindowWidthSizeClass.CompactMaxWidth: Dp
    get() = 600.dp

val WindowWidthSizeClass.MediumMinWidth: Dp
    get() = CompactMaxWidth

val WindowWidthSizeClass.MediumMaxWidth: Dp
    get() = 840.dp

val WindowWidthSizeClass.ExpandedMinWidth: Dp
    get() = MediumMaxWidth

// height - https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes
val WindowHeightSizeClass.CompactMaxHeight: Dp
    get() = 480.dp

val WindowHeightSizeClass.MediumMinHeight: Dp
    get() = CompactMaxHeight

val WindowHeightSizeClass.MediumMaxHeight: Dp
    get() = 900.dp

val WindowHeightSizeClass.ExpandedMinHeight: Dp
    get() = MediumMaxHeight
