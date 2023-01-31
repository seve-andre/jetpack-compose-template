package com.mitch.appname.data.local.datastore.user.preferences

import com.mitch.appname.util.AppTheme
import kotlinx.coroutines.flow.Flow

/**
 * [UserPreferencesRepo] is used to retrieve/set [UserPreferences] data
 */
interface UserPreferencesRepo {
    val userPreferencesData: Flow<UserPreferences>
    suspend fun setTheme(theme: AppTheme)
    fun getTheme(): Flow<AppTheme>
}
