package com.mitch.template.data.settings

import com.mitch.template.domain.models.TemplateLanguage
import com.mitch.template.domain.models.TemplateTheme
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getTheme(): Flow<TemplateTheme>
    suspend fun setTheme(theme: TemplateTheme)

    fun getLanguage(): Flow<TemplateLanguage>
    fun setLanguage(language: TemplateLanguage)
}
