package com.mitch.appname.data.local.datastore.user.preferences

import androidx.datastore.core.DataStore
import com.mitch.appname.ProtoUserPreferences
import com.mitch.appname.ProtoUserPreferences.ProtoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * [UserPreferencesLocalDataSource] is the mediator between [ProtoUserPreferences] Datastore and
 * the repo to exchange data from the Datastore file
 *
 * @property userPreferences is the actual [ProtoUserPreferences] Datastore
 */
class UserPreferencesLocalDataSource @Inject constructor(
    private val userPreferences: DataStore<ProtoUserPreferences>
) {
    suspend fun setProtoTheme(theme: ProtoAppTheme) {
        userPreferences.updateData {
            it.toBuilder().setTheme(theme).build()
        }
    }

    fun getProtoTheme(): Flow<ProtoAppTheme> = userPreferences.data.map { it.theme }
}
