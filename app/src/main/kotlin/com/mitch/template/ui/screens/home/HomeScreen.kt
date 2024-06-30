package com.mitch.template.ui.screens.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.template.R
import com.mitch.template.domain.models.TemplateLanguage
import com.mitch.template.domain.models.TemplateTheme
import com.mitch.template.ui.designsystem.components.loading.LoadingScreen
import com.mitch.template.ui.screens.home.components.LanguagePickerDialog
import com.mitch.template.ui.screens.home.components.ThemePickerDialog

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onChangeTheme = viewModel::updateTheme,
        onChangeLanguage = viewModel::updateLanguage
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onChangeTheme: (TemplateTheme) -> Unit,
    onChangeLanguage: (TemplateLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        HomeUiState.Loading -> LoadingScreen()

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

enum class ActiveDialog {
    None, Language, Theme
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    HomeScreen(
        uiState = HomeUiState.Success(
            language = TemplateLanguage.English,
            theme = TemplateTheme.Light
        ),
        onChangeTheme = { },
        onChangeLanguage = { }
    )
}
