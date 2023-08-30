package com.mitch.appname.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.appname.ui.screens.home.components.LanguagePickerDialog
import com.mitch.appname.ui.screens.home.components.ThemePickerDialog
import com.mitch.appname.util.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import java.util.Locale

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val theme by viewModel.theme.collectAsStateWithLifecycle()
    val locale by viewModel.locale.collectAsStateWithLifecycle()

    HomeScreen(
        theme = theme,
        onChangeTheme = viewModel::updateTheme,
        locale = locale,
        onChangeLanguage = viewModel::updateLocale
    )
}

@Composable
fun HomeScreen(
    theme: AppTheme,
    onChangeTheme: (AppTheme) -> Unit,
    locale: Locale,
    onChangeLanguage: (Locale) -> Unit,
    modifier: Modifier = Modifier
) {
    val (activeDialog, setActiveDialog) = remember { mutableStateOf<ActiveDialog>(ActiveDialog.None) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { setActiveDialog(ActiveDialog.Language) }) {
            Text(text = "Change language")
        }

        Button(onClick = { setActiveDialog(ActiveDialog.Theme) }) {
            Text(text = "Change theme")
        }

        when (activeDialog) {
            ActiveDialog.None -> Unit

            ActiveDialog.Language -> {
                LanguagePickerDialog(
                    selectedLocale = locale,
                    onDismiss = { setActiveDialog(ActiveDialog.None) },
                    onConfirm = onChangeLanguage
                )
            }

            ActiveDialog.Theme -> {
                ThemePickerDialog(
                    selectedTheme = theme,
                    onDismiss = { setActiveDialog(ActiveDialog.None) },
                    onConfirm = onChangeTheme
                )
            }
        }
    }
}

sealed interface ActiveDialog {
    data object None : ActiveDialog
    data object Language : ActiveDialog
    data object Theme : ActiveDialog
}
