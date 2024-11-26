package com.mitch.template.ui.screens.home

import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.domain.models.TemplateThemePreference

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
