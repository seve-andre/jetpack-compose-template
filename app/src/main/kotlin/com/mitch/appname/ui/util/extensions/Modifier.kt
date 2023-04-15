package com.mitch.appname.ui.util.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.fade
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer

/**
 * Basic skeleton loader/placeholder effect
 *
 * see [basic usage](https://google.github.io/accompanist/placeholder/#basic-usage)
 *
 * @param visible whether the skeleton should be visible or not
 * @return [Modifier] with the [placeholder] effect applied
 */
fun Modifier.simpleSkeleton(
    visible: Boolean
): Modifier = then(
    placeholder(visible = visible)
)

/**
 * Fade skeleton effect
 *
 * see [fade effect](https://google.github.io/accompanist/placeholder/#fade)
 *
 * @param visible whether the skeleton should be visible or not
 * @return [Modifier] with the fade [placeholder] effect applied
 */
fun Modifier.fadeSkeleton(
    visible: Boolean
): Modifier = composed {
    placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.fade()
    )
}

/**
 * Shimmer skeleton effect
 *
 * see [shimmer effect](https://google.github.io/accompanist/placeholder/#shimmer)
 *
 * @param visible whether the skeleton should be visible or not
 * @return [Modifier] with the shimmer [placeholder] effect applied
 */
fun Modifier.shimmerSkeleton(
    visible: Boolean
): Modifier = composed {
    placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}
