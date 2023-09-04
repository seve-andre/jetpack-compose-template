package com.mitch.appname.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.appname.R
import com.mitch.appname.ui.screens.home.components.LanguagePickerDialog
import com.mitch.appname.ui.screens.home.components.ThemePickerDialog
import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import timber.log.Timber

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Timber.d("HomeUiState: $uiState")

    HomeScreen(
        uiState = uiState,
        onChangeTheme = viewModel::updateTheme,
        onChangeLanguage = viewModel::updateLanguage
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onChangeTheme: (AppTheme) -> Unit,
    onChangeLanguage: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        HomeUiState.Loading -> Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

        is HomeUiState.Success -> {
            val (activeDialog, setActiveDialog) = remember {
                mutableStateOf<ActiveDialog>(
                    ActiveDialog.None
                )
            }

            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { setActiveDialog(ActiveDialog.Language) }) {
                    Text(text = stringResource(id = R.string.change_language))
                }

                Button(onClick = { setActiveDialog(ActiveDialog.Theme) }) {
                    Text(text = stringResource(R.string.change_theme))
                }

                when (activeDialog) {
                    ActiveDialog.None -> Unit

                    ActiveDialog.Language -> {
                        LanguagePickerDialog(
                            selectedLanguage = uiState.language,
                            onDismiss = { setActiveDialog(ActiveDialog.None) },
                            onConfirm = onChangeLanguage
                        )
                    }

                    ActiveDialog.Theme -> {
                        ThemePickerDialog(
                            selectedTheme = uiState.theme,
                            onDismiss = { setActiveDialog(ActiveDialog.None) },
                            onConfirm = onChangeTheme
                        )
                    }
                }
            }
        }

        is HomeUiState.Error -> Unit
    }
}

sealed interface ActiveDialog {
    data object None : ActiveDialog
    data object Language : ActiveDialog
    data object Theme : ActiveDialog
}
