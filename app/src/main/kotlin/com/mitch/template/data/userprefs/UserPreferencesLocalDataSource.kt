package com.mitch.template.data.userprefs

import androidx.datastore.core.DataStore
import com.mitch.template.domain.models.TemplateThemeConfig
import com.mitch.template.domain.models.UserPreferences
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
    suspend fun setTheme(theme: TemplateThemeConfig) {
        userPreferences.updateData {
            it.copy(theme = theme)
        }
    }

    fun getTheme(): Flow<TemplateThemeConfig> = userPreferences.data.map { it.theme }
}
