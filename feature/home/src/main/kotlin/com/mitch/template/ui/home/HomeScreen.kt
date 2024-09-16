package com.mitch.template.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mitch.template.core.designsystem.TemplateTheme
import com.mitch.template.core.designsystem.components.loading.LoadingScreen
import com.mitch.template.core.domain.models.TemplateLanguageConfig
import com.mitch.template.core.domain.models.TemplateThemeConfig
import com.mitch.template.ui.home.ActiveDialog.Language
import com.mitch.template.ui.home.ActiveDialog.None
import com.mitch.template.ui.home.ActiveDialog.Theme
import com.mitch.template.ui.home.components.LanguagePickerDialog
import com.mitch.template.ui.home.components.ThemePickerDialog

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
    onChangeTheme: (TemplateThemeConfig) -> Unit,
    onChangeLanguage: (TemplateLanguageConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        HomeUiState.Loading -> LoadingScreen()

        is HomeUiState.Success -> {
            var activeDialog by remember { mutableStateOf(None) }
            when (activeDialog) {
                None -> Unit

                Language -> LanguagePickerDialog(
                    selectedLanguage = uiState.language,
                    onDismiss = { activeDialog = None },
                    onConfirm = onChangeLanguage
                )

                Theme -> ThemePickerDialog(
                    selectedTheme = uiState.theme,
                    onDismiss = { activeDialog = None },
                    onConfirm = onChangeTheme
                )
            }

            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { activeDialog = Language }) {
                    Text(text = stringResource(id = R.string.feature_home_change_language))
                }

                Button(onClick = { activeDialog = Theme }) {
                    Text(text = stringResource(R.string.feature_home_change_theme))
                }
            }
        }

        is HomeUiState.Error -> Unit
    }
}

private enum class ActiveDialog {
    None, Language, Theme
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    TemplateTheme {
        HomeScreen(
            uiState = HomeUiState.Success(
                language = TemplateLanguageConfig.English,
                theme = TemplateThemeConfig.Light
            ),
            onChangeTheme = { },
            onChangeLanguage = { }
        )
    }
}
