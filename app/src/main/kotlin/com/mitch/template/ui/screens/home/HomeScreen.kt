package com.mitch.template.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.template.R
import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.ui.designsystem.TemplateTheme
import com.mitch.template.ui.designsystem.components.loading.LoadingScreen
import com.mitch.template.ui.screens.home.components.LanguagePickerDialog
import com.mitch.template.ui.screens.home.components.ThemePickerDialog
import kotlinx.collections.immutable.toPersistentSet

@Composable
fun HomeRoute(viewModel: HomeViewModel) {
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
    onChangeTheme: (TemplateThemePreference) -> Unit,
    onChangeLanguage: (TemplateLanguagePreference) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        HomeUiState.Loading -> LoadingScreen()

        is HomeUiState.Success -> {
            var activeDialog by rememberSaveable {
                mutableStateOf(ActiveDialog.None)
            }

            when (activeDialog) {
                ActiveDialog.None -> Unit

                ActiveDialog.Language -> LanguagePickerDialog(
                    onDismiss = { activeDialog = ActiveDialog.None },
                    onConfirm = onChangeLanguage,
                    selectedLanguage = uiState.language,
                    languageOptions = TemplateLanguagePreference.entries.toPersistentSet()
                )

                ActiveDialog.Theme -> ThemePickerDialog(
                    onDismiss = { activeDialog = ActiveDialog.None },
                    onConfirm = onChangeTheme,
                    selectedTheme = uiState.theme,
                    themeOptions = TemplateThemePreference.entries.toPersistentSet()
                )
            }

            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { activeDialog = ActiveDialog.Language }) {
                    Text(text = stringResource(id = R.string.change_language))
                }

                Button(onClick = { activeDialog = ActiveDialog.Theme }) {
                    Text(text = stringResource(R.string.change_theme))
                }
            }
        }

        is HomeUiState.Error -> Unit
    }
}

private enum class ActiveDialog {
    None, Language, Theme
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun HomeScreenContentPreview() {
    TemplateTheme {
        HomeScreen(
            uiState = HomeUiState.Success(
                language = TemplateLanguagePreference.English,
                theme = TemplateThemePreference.Light
            ),
            onChangeTheme = { },
            onChangeLanguage = { }
        )
    }
}
