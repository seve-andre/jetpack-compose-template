package com.mitch.appname.data.local.datastore.user.preferences

import androidx.datastore.core.DataStore
import com.mitch.appname.ProtoUserPreferences
import com.mitch.appname.ProtoUserPreferences.ProtoAppLanguage
import com.mitch.appname.ProtoUserPreferences.ProtoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<ProtoUserPreferences>
) {
    suspend fun setProtoLanguage(language: ProtoAppLanguage) {
        userPreferences.updateData {
            it.toBuilder().setLanguage(language).build()
        }
    }

    suspend fun setProtoTheme(theme: ProtoAppTheme) {
        userPreferences.updateData {
            it.toBuilder().setTheme(theme).build()
        }
    }

    fun getProtoLanguage(): Flow<ProtoAppLanguage> = userPreferences.data.map { it.language }
    fun getProtoTheme(): Flow<ProtoAppTheme> = userPreferences.data.map { it.theme }
}
