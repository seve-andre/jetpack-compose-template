package com.mitch.template.data.settings

import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.domain.models.TemplateUserPreferences
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    val preferences: Flow<TemplateUserPreferences>
    suspend fun initLocaleIfNeeded()
    suspend fun setTheme(theme: TemplateThemePreference)
    suspend fun setLanguage(language: TemplateLanguagePreference)
}
