package com.mitch.appname.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitch.appname.domain.repo.AppLanguageRepo
import com.mitch.appname.domain.repo.UserPreferencesRepo
import com.mitch.appname.util.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepo: UserPreferencesRepo,
    private val appLanguageRepo: AppLanguageRepo
) : ViewModel() {

    val locale = appLanguageRepo.getLocale()
        .map {
            Locale.forLanguageTag(it.language)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Locale.getDefault()
        )

    val theme = userPreferencesRepo.userPreferencesData
        .map { it.theme }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppTheme.FOLLOW_SYSTEM
        )

    fun updateTheme(theme: AppTheme) {
        viewModelScope.launch {
            userPreferencesRepo.setTheme(theme)
        }
    }

    fun updateLocale(locale: Locale) {
        viewModelScope.launch {
            appLanguageRepo.setLocale(locale)
        }
    }
}
