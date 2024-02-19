package com.mitch.appname.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mitch.appname.navigation.NavGraphs
import com.mitch.appname.navigation.appCurrentDestinationAsState
import com.mitch.appname.navigation.appDestination
import com.mitch.appname.navigation.destinations.Destination
import com.mitch.appname.navigation.startAppDestination
import com.mitch.appname.util.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    networkMonitor: NetworkMonitor,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(navController, snackbarHostState, coroutineScope, networkMonitor) {
        AppState(navController, snackbarHostState, coroutineScope, networkMonitor)
    }
}

// Controls app state. Stable -> if any of the values is changed, the Composables are recomposed
@Stable
class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    /**
     * App's current [Destination] if set, otherwise starting destination.
     *
     * Starting destination: search for `@RootNavGraph(start = true)`
     */
    val currentDestination: Destination
        @Composable get() = navController.appCurrentDestinationAsState().value
            ?: NavGraphs.root.startAppDestination

    /**
     * App's previous destination if set, otherwise null
     */
    val prevDestination: Destination?
        @Composable get() = navController.previousBackStackEntry?.appDestination()

    /**
     * Manages app connectivity status
     */
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
fun NavController.navigateToPreviousScreen() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}
