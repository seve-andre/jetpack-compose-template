package com.mitch.template.feature.home

import com.mitch.template.core.domain.models.TemplateLanguageConfig
import com.mitch.template.core.domain.models.TemplateThemeConfig

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
