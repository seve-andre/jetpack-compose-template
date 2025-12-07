package com.mitch.template.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mitch.template.util.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberTemplateAppState(
    networkMonitor: NetworkMonitor,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): TemplateAppState {
    return remember(snackbarHostState, coroutineScope, networkMonitor) {
        TemplateAppState(
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor
        )
    }
}

@Stable
class TemplateAppState(
    val snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    /**
     * Manages app connectivity status
     */
    val isOffline: StateFlow<Boolean> = networkMonitor.networkInfo
        .map { it.isOnline }
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}
