package com.mitch.template.ui.screens.home

import com.mitch.template.domain.models.TemplateLanguageConfig
import com.mitch.template.domain.models.TemplateThemeConfig

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: TemplateLanguageConfig,
        val theme: TemplateThemeConfig
    ) : HomeUiState
}
