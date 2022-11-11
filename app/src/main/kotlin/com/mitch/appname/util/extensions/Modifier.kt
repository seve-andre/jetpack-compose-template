package com.mitch.appname.util.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

fun Modifier.simpleSkeleton(
    visible: Boolean
): Modifier = then(
    placeholder(visible = visible)
)

fun Modifier.fadeSkeleton(
    visible: Boolean
): Modifier = composed {
    placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.fade()
    )
}

fun Modifier.shimmerSkeleton(
    visible: Boolean
): Modifier = composed {
    placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}
