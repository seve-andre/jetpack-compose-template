package com.mitch.template.core.data.settings

import com.mitch.template.core.data.language.LanguageLocalDataSource
import com.mitch.template.core.data.language.toDomainLanguage
import com.mitch.template.core.datastore.userprefs.UserPreferencesLocalDataSource
import com.mitch.template.core.domain.UserSettingsRepository
import com.mitch.template.core.domain.models.TemplateLanguageConfig
import com.mitch.template.core.domain.models.TemplateThemeConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultUserSettingsRepository @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override fun getTheme(): Flow<TemplateThemeConfig> {
        return userPreferencesLocalDataSource.getTheme().map { it }
    }

    override suspend fun setTheme(theme: TemplateThemeConfig) {
        userPreferencesLocalDataSource.setTheme(theme)
    }

    override fun getLanguage(): Flow<TemplateLanguageConfig> {
        return languageLocalDataSource.getLocale().map { it.toDomainLanguage() }
    }

    override suspend fun setLanguage(language: TemplateLanguageConfig) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
