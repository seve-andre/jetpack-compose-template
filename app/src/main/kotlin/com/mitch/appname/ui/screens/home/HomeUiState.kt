package com.mitch.appname.ui.screens.home

import com.mitch.appname.util.AppTheme
import java.util.Locale

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Error(
        val error: String? = null
    ) : HomeUiState

    data class Success(
        val language: Locale,
        val theme: AppTheme
    ) : HomeUiState
}
