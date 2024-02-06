package com.mitch.appname.domain.repository

import com.mitch.appname.domain.models.AppLanguage
import com.mitch.appname.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {
    fun getTheme(): Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)

    fun getLanguage(): Flow<AppLanguage>
    fun setLanguage(language: AppLanguage)
}
