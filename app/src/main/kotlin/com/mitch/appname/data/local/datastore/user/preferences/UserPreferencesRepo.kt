package com.mitch.appname.data.local.datastore.user.preferences

import com.mitch.appname.util.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepo {
    val userPreferencesData: Flow<UserPreferences>

    suspend fun setTheme(theme: AppTheme)
    fun getTheme(): Flow<AppTheme>
}
