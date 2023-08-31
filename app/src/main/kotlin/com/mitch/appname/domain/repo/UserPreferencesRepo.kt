package com.mitch.appname.domain.repo

import com.mitch.appname.domain.model.UserPreferences
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
