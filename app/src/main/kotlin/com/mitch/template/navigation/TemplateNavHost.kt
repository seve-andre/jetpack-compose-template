package com.mitch.template.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mitch.template.feature.home.HomeRoute
import com.mitch.template.navigation.TemplateDestination.Screen

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
    destination: TemplateDestination,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): () -> Unit = dropUnlessResumed {
    this.navigate(destination, navOptions, navigatorExtras)
}
