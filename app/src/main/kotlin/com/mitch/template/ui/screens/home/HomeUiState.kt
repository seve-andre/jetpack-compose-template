package com.mitch.template.ui.screens.home

import com.mitch.template.domain.models.TemplateLanguage
import com.mitch.template.domain.models.TemplateTheme

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: TemplateLanguage,
        val theme: TemplateTheme
    ) : HomeUiState
}
