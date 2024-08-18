package com.mitch.template.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mitch.template.ui.navigation.TemplateDestination.Graph
import com.mitch.template.ui.navigation.TemplateDestination.Screen
import com.mitch.template.ui.screens.home.HomeRoute

@Composable
fun TemplateNavHost(
    navController: NavHostController,
    startDestination: TemplateDestination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Home> {
            HomeRoute()
        }
    }
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
