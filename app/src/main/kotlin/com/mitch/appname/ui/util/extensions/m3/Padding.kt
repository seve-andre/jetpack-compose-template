package com.mitch.appname.ui.util.extensions.m3

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.unit.Dp
import com.mitch.appname.ui.theme.custom.padding

/**
 * [Card] left/right padding according to Material3
 *
 * see value at [Material3 Card Layout values](https://m3.material.io/components/cards/specs#9abbced9-d5d3-4893-9a67-031825205f06)
 */
val CardDefaults.contentPadding: Dp
    get() = padding.medium

/**
 * Padding between multiple [Card]
 *
 * see value at [Material3 Card Layout values](https://m3.material.io/components/cards/specs#9abbced9-d5d3-4893-9a67-031825205f06)
 */
val CardDefaults.paddingBetweenCards: Dp
    get() = padding.small
