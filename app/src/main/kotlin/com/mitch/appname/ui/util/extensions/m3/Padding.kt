package com.mitch.appname.ui.util.extensions.m3

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mitch.appname.ui.designsystem.theme.custom.LocalPadding

/**
 * [Card] left/right padding according to Material3
 *
 * see value at [Material3 Card Layout values](https://m3.material.io/components/cards/specs#9abbced9-d5d3-4893-9a67-031825205f06)
 */
val CardDefaults.contentPadding: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current.medium

/**
 * Padding between multiple [Card]
 *
 * see value at [Material3 Card Layout values](https://m3.material.io/components/cards/specs#9abbced9-d5d3-4893-9a67-031825205f06)
 */
val CardDefaults.paddingBetweenCards: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current.small

/**
 * [AlertDialog] top/left/right/bottom padding according to Material3
 *
 * see value at [Material3 Dialog Layout values](https://m3.material.io/components/dialogs/specs#6771d107-624e-47cc-b6d8-2b7b620ba2f1)
 */
val AlertDialogDefaults.contentPadding: Dp
    get() = 24.dp

/**
 * [AlertDialog] padding between buttons according to Material3
 *
 * see value at [Material3 Dialog Layout values](https://m3.material.io/components/dialogs/specs#6771d107-624e-47cc-b6d8-2b7b620ba2f1)
 */
val AlertDialogDefaults.buttonsPadding: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current.small

/**
 * [AlertDialog] padding between icon and title according to Material3
 *
 * see value at [Material3 Dialog Layout values](https://m3.material.io/components/dialogs/specs#6771d107-624e-47cc-b6d8-2b7b620ba2f1)
 */
val AlertDialogDefaults.paddingIconTitle: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current.medium

/**
 * [AlertDialog] padding between title and body according to Material3
 *
 * see value at [Material3 Dialog Layout values](https://m3.material.io/components/dialogs/specs#6771d107-624e-47cc-b6d8-2b7b620ba2f1)
 */
val AlertDialogDefaults.paddingTitleBody: Dp
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current.medium

/**
 * [AlertDialog] padding between body and buttons according to Material3
 *
 * see value at [Material3 Dialog Layout values](https://m3.material.io/components/dialogs/specs#6771d107-624e-47cc-b6d8-2b7b620ba2f1)
 */
val AlertDialogDefaults.paddingBodyButtons: Dp
    get() = 24.dp
