package com.mitch.appname.ui.screens.home

import com.mitch.appname.domain.models.AppLanguage
import com.mitch.appname.domain.models.AppTheme

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: AppLanguage,
        val theme: AppTheme
    ) : HomeUiState
}
