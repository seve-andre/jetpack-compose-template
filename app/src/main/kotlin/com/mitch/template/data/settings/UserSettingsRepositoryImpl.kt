package com.mitch.template.data.settings

import com.mitch.template.data.language.LanguageLocalDataSource
import com.mitch.template.data.language.toDomainLanguage
import com.mitch.template.data.userprefs.UserPreferencesLocalDataSource
import com.mitch.template.domain.models.TemplateLanguage
import com.mitch.template.domain.models.TemplateTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override fun getTheme(): Flow<TemplateTheme> {
        return userPreferencesLocalDataSource.getTheme().map { it }
    }

    override suspend fun setTheme(theme: TemplateTheme) {
        userPreferencesLocalDataSource.setTheme(theme)
    }

    override fun getLanguage(): Flow<TemplateLanguage> {
        return languageLocalDataSource.getLocale().map { it.toDomainLanguage() }
    }

    override fun setLanguage(language: TemplateLanguage) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
