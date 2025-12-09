package com.mitch.template.ui.screens.home

import androidx.compose.runtime.Stable
import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.domain.models.TemplateThemePreference

@Stable
sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: TemplateLanguagePreference,
        val theme: TemplateThemePreference
    ) : HomeUiState
}
