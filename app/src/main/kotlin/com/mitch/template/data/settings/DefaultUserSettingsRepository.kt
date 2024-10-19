package com.mitch.template.data.settings

import com.mitch.template.data.language.LanguageLocalDataSource
import com.mitch.template.data.language.toDomainLanguage
import com.mitch.template.data.userprefs.UserPreferencesLocalDataSource
import com.mitch.template.data.userprefs.toDomainModel
import com.mitch.template.data.userprefs.toProtoModel
import com.mitch.template.domain.models.TemplateLanguageConfig
import com.mitch.template.domain.models.TemplateThemeConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultUserSettingsRepository(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override fun getTheme(): Flow<TemplateThemeConfig> {
        return userPreferencesLocalDataSource.getTheme().map { it.toDomainModel() }
    }

    override suspend fun setTheme(theme: TemplateThemeConfig) {
        userPreferencesLocalDataSource.setTheme(theme.toProtoModel())
    }

    override fun getLanguage(): Flow<TemplateLanguageConfig> {
        return languageLocalDataSource.getLocale().map { it.toDomainLanguage() }
    }

    override suspend fun setLanguage(language: TemplateLanguageConfig) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
