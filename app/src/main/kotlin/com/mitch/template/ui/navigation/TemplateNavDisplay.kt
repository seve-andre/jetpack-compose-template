package com.mitch.template.ui.navigation

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.mitch.template.di.DependenciesProvider
import com.mitch.template.ui.designsystem.components.snackbars.SnackbarEvent
import com.mitch.template.ui.screens.home.HomeRoute
import com.mitch.template.ui.screens.home.HomeViewModel
import com.mitch.template.ui.util.viewModelProviderFactory

@Composable
fun TemplateNavDisplay(
    onShowSnackbar: suspend (SnackbarEvent) -> SnackbarResult,
    dependenciesProvider: DependenciesProvider,
    backStack: NavBackStack<NavKey>
) {
    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<TemplateNavKey.Home> {
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
    )
}
