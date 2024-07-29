package com.mitch.template.data.settings

import com.mitch.template.domain.models.TemplateLanguageConfig
import com.mitch.template.domain.models.TemplateThemeConfig
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getTheme(): Flow<TemplateThemeConfig>
    suspend fun setTheme(theme: TemplateThemeConfig)

    fun getLanguage(): Flow<TemplateLanguageConfig>
    suspend fun setLanguage(language: TemplateLanguageConfig)
}
