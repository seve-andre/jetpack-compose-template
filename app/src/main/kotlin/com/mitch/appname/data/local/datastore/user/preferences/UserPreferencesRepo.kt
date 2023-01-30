package com.mitch.appname.data.local.datastore.user.preferences

import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepo {
    val userPreferencesData: Flow<UserPreferences>

    suspend fun setLanguage(language: AppLanguage)
    suspend fun setTheme(theme: AppTheme)
    fun getLanguage(): Flow<AppLanguage>
    fun getTheme(): Flow<AppTheme>
}
