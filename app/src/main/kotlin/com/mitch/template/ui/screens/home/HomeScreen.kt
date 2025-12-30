package com.mitch.template.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
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
                    onConfirm = { newLanguage ->
                        if (newLanguage != uiState.language) {
                            onChangeLanguage(newLanguage)
                        }
                        activeDialog = ActiveDialog.None
                    },
                    selectedLanguage = uiState.language,
                    languageOptions = TemplateLanguagePreference.entries.toPersistentSet()
                )

                ActiveDialog.Theme -> ThemePickerDialog(
                    onDismiss = { activeDialog = ActiveDialog.None },
                    onConfirm = { newTheme ->
                        if (newTheme != uiState.theme) {
                            onChangeTheme(newTheme)
                        }
                        activeDialog = ActiveDialog.None
                    },
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
                Spacer(modifier = Modifier.height(10.dp))
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

@Preview(name = "Light", showBackground = true, showSystemUi = true)
@Preview(
    name = "Dark",
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    showBackground = true,
    showSystemUi = true
)
@PreviewScreenSizes
@PreviewFontScale
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
