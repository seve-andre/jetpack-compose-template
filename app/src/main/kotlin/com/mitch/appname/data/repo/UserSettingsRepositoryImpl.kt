package com.mitch.appname.data.repo

import com.mitch.appname.data.local.LanguageLocalDataSource
import com.mitch.appname.data.local.datastore.user.preferences.UserPreferencesLocalDataSource
import com.mitch.appname.data.mapper.toAppLanguage
import com.mitch.appname.data.mapper.toLocal
import com.mitch.appname.data.mapper.toProto
import com.mitch.appname.domain.repo.UserSettingsRepository
import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userPreferencesLocalDataSource: UserPreferencesLocalDataSource,
    private val languageLocalDataSource: LanguageLocalDataSource
) : UserSettingsRepository {

    override fun getTheme(): Flow<AppTheme> {
        return userPreferencesLocalDataSource.getProtoTheme().map { it.toLocal() }
    }

    override suspend fun setTheme(theme: AppTheme) {
        userPreferencesLocalDataSource.setProtoTheme(theme.toProto())
    }

    override fun getLanguage(): Flow<AppLanguage> {
        return languageLocalDataSource.getLocale().map { it.toAppLanguage() }
    }

    override fun setLanguage(language: AppLanguage) {
        languageLocalDataSource.setLocale(language.locale)
    }
}
