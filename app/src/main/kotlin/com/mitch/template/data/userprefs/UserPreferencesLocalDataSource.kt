package com.mitch.template.data.userprefs

import androidx.datastore.core.DataStore
import com.mitch.template.data.userprefs.ProtoUserPreferences.ProtoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * [UserPreferencesLocalDataSource] is the mediator between [ProtoUserPreferences] Datastore and
 * the repo to exchange data from the Datastore file
 *
 * @property userPreferences is the actual [ProtoUserPreferences] Datastore
 */
class UserPreferencesLocalDataSource(
    private val userPreferences: DataStore<ProtoUserPreferences>
) {
    suspend fun setTheme(theme: ProtoAppTheme) {
        userPreferences.updateData {
            it.copy {
                this.theme = theme
            }
        }
    }

    fun getTheme(): Flow<ProtoAppTheme> = userPreferences.data.map { it.theme }
}
