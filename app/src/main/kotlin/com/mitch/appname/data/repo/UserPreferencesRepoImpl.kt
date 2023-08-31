package com.mitch.appname.data.repo

import com.mitch.appname.data.local.datastore.user.preferences.UserPreferencesDataSource
import com.mitch.appname.data.mapper.toLocal
import com.mitch.appname.data.mapper.toLocalUserPreferences
import com.mitch.appname.data.mapper.toProto
import com.mitch.appname.domain.repo.UserPreferencesRepo
import com.mitch.appname.util.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * [UserPreferencesRepoImpl] is the actual [UserPreferencesRepo] implementation that makes use of
 * [UserPreferencesDataSource] to retrieve/set Datastore preferences, using a local mapper
 *
 * @property userPreferencesDataSource the mediator between Datastore and Repository
 */
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
