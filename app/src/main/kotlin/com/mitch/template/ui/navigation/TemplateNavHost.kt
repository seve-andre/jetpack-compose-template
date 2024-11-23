package com.mitch.template.ui.navigation

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mitch.template.di.DependenciesProvider
import com.mitch.template.ui.designsystem.components.snackbars.SnackbarEvent
import com.mitch.template.ui.navigation.TemplateDestination.Screen
import com.mitch.template.ui.screens.home.HomeRoute
import com.mitch.template.ui.screens.home.HomeViewModel
import com.mitch.template.ui.util.viewModelProviderFactory

@Composable
fun TemplateNavHost(
    onShowSnackbar: suspend (SnackbarEvent) -> SnackbarResult,
    dependenciesProvider: DependenciesProvider,
    navController: NavHostController,
    startDestination: TemplateDestination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Home> {
            HomeRoute(
                viewModel = viewModel(
                    factory = viewModelProviderFactory {
                        HomeViewModel(
                            userSettingsRepository = dependenciesProvider.userSettingsRepository
                        )
                    }
                )
            )
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
