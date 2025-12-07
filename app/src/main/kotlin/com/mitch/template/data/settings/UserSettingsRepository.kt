package com.mitch.template.data.settings

import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.domain.models.TemplateUserPreferences
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    val preferences: Flow<TemplateUserPreferences>
    suspend fun initLocale()
    suspend fun setTheme(theme: TemplateThemePreference)
    suspend fun setLanguage(language: TemplateLanguagePreference)
}
