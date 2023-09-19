package com.mitch.appname.ui.screen.home

import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme

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
