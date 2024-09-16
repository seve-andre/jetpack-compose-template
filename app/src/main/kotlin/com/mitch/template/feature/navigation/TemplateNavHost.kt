package com.mitch.template.feature.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mitch.template.feature.home.HomeRoute
import com.mitch.template.feature.navigation.TemplateDestinations.Graph
import com.mitch.template.feature.navigation.TemplateDestinations.Screen

@Composable
fun TemplateNavHost(
    navController: NavHostController,
    startDestination: TemplateStartDestination
) {
    NavHost(
        navController = navController,
        startDestination = when (startDestination) {
            is TemplateStartDestination.Screen -> startDestination.screen
            is TemplateStartDestination.Graph -> startDestination.graph
        }
    ) {
        composable<Screen.Home> {
            HomeRoute()
        }
    }
}

sealed interface TemplateStartDestination {
    data class Screen(val screen: TemplateDestinations.Screen) : TemplateStartDestination
    data class Graph(val graph: TemplateDestinations.Graph) : TemplateStartDestination
}

// dropUnlessResumed is used to avoid navigating multiple times to the same destination or
// popping the backstack when the destination is already on top.
@Composable
fun NavController.navigateTo(
    screen: Screen,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): () -> Unit = dropUnlessResumed {
    this.navigate(screen, navOptions, navigatorExtras)
}

@Composable
fun NavController.navigateTo(
    graph: Graph,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): () -> Unit = dropUnlessResumed {
    this.navigate(graph, navOptions, navigatorExtras)
}
