package com.mitch.appname.data.userprefs

import androidx.datastore.core.DataStore
import com.mitch.appname.domain.models.AppTheme
import com.mitch.appname.domain.models.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * [UserPreferencesLocalDataSource] is the mediator between [UserPreferences] Datastore and
 * the repo to exchange data from the Datastore file
 *
 * @property userPreferences is the actual [UserPreferences] Datastore
 */
class UserPreferencesLocalDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    suspend fun setTheme(theme: AppTheme) {
        userPreferences.updateData {
            it.copy(theme = theme)
        }
    }

    fun getTheme(): Flow<AppTheme> = userPreferences.data.map { it.theme }
}
