package com.mitch.appname.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun Modifier.simpleSkeleton(
    visible: Boolean
): Modifier = this.placeholder(visible = visible)

@Composable
fun Modifier.fadeSkeleton(
    visible: Boolean
): Modifier = this.placeholder(
    visible = visible,
    highlight = PlaceholderHighlight.fade()
)

@Composable
fun Modifier.shimmerSkeleton(
    visible: Boolean
): Modifier = this.placeholder(
    visible = visible,
    highlight = PlaceholderHighlight.shimmer()
)
