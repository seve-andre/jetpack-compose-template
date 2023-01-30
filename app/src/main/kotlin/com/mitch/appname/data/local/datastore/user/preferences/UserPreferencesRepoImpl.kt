package com.mitch.appname.data.local.datastore.user.preferences

import com.mitch.appname.data.mapper.toLocal
import com.mitch.appname.data.mapper.toLocalUserPreferences
import com.mitch.appname.data.mapper.toProto
import com.mitch.appname.util.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepoImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) : UserPreferencesRepo {

    override val userPreferencesData =
        userPreferencesDataSource.userPreferencesData.map { it.toLocalUserPreferences() }

    override suspend fun setTheme(theme: AppTheme) =
        userPreferencesDataSource.setProtoTheme(theme.toProto())

    override fun getTheme(): Flow<AppTheme> =
        userPreferencesDataSource.getProtoTheme().map { it.toLocal() }
}
