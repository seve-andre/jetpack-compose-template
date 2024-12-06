package com.mitch.template.data.settings

import com.mitch.template.data.language.LanguageLocalDataSource
import com.mitch.template.data.language.toDomainModel
import com.mitch.template.data.userprefs.UserPreferencesLocalDataSource
import com.mitch.template.data.userprefs.toDomainModel
import com.mitch.template.data.userprefs.toProtoModel
import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.domain.models.TemplateUserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class DefaultUserSettingsRepository(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override val preferences: Flow<TemplateUserPreferences> = combine(
        userPreferencesLocalDataSource.protoPreferences,
        languageLocalDataSource.getLocale()
    ) { protoPreferences, locale ->
        TemplateUserPreferences(
            theme = protoPreferences.theme.toDomainModel(),
            language = locale.toDomainModel()
        )
    }

    override suspend fun setTheme(theme: TemplateThemePreference) {
        userPreferencesLocalDataSource.setTheme(theme.toProtoModel())
    }

    override suspend fun setLanguage(language: TemplateLanguagePreference) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
