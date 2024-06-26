package com.mitch.template.ui.navigation

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mitch.template.ui.designsystem.components.snackbars.TemplateSnackbarVisuals
import com.mitch.template.ui.navigation.TemplateDestinations.Graph
import com.mitch.template.ui.navigation.TemplateDestinations.Screen
import com.mitch.template.ui.screens.home.HomeRoute

@Composable
fun TemplateNavHost(
    navController: NavHostController,
    startDestination: TemplateStartDestination,
    onShowSnackbar: suspend (TemplateSnackbarVisuals) -> SnackbarResult
) {
    NavHost(
        navController = navController,
        startDestination = when (startDestination) {
            is TemplateStartDestination.Screen -> startDestination.screen
            is TemplateStartDestination.Graph -> startDestination.graph
        }
    ) {
        composable<Screen.Home> { HomeRoute() }
    }
}

sealed interface TemplateStartDestination {
    data class Screen(val screen: TemplateDestinations.Screen) : TemplateStartDestination
    data class Graph(val graph: TemplateDestinations.Graph) : TemplateStartDestination
}

fun NavController.navigateTo(
    screen: Screen,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    this.navigate(screen, navOptions, navigatorExtras)
}

fun NavController.navigateTo(
    graph: Graph,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    this.navigate(graph, navOptions, navigatorExtras)
}
