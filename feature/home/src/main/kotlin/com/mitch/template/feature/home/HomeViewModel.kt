package com.mitch.template.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.template.core.domain.models.TemplateLanguageConfig
import com.mitch.template.core.domain.models.TemplateThemeConfig
import com.mitch.template.core.util.Result
import com.mitch.template.core.util.asResult
import com.mitch.template.feature.home.HomeUiState.Error
import com.mitch.template.feature.home.HomeUiState.Loading
import com.mitch.template.feature.home.HomeUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userSettingsRepository: com.mitch.template.core.domain.UserSettingsRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        userSettingsRepository.getLanguage(),
        userSettingsRepository.getTheme(),
        ::Pair
    ).asResult()
        .map { result ->
            when (result) {
                Result.Loading -> Loading
                is Result.Success -> {
                    val (language, theme) = result.data

                    Success(
                        language = language,
                        theme = theme
                    )
                }

                is Result.Error -> Error()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Loading
        )

    fun updateTheme(theme: TemplateThemeConfig) {
        viewModelScope.launch {
            userSettingsRepository.setTheme(theme)
        }
    }

    fun updateLanguage(language: TemplateLanguageConfig) {
        viewModelScope.launch {
            userSettingsRepository.setLanguage(language)
        }
    }
}
