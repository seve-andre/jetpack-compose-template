package com.mitch.template.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.mitch.template.data.TemplateDatabase
import com.mitch.template.data.language.LanguageLocalDataSource
import com.mitch.template.data.settings.DefaultUserSettingsRepository
import com.mitch.template.data.settings.UserSettingsRepository
import com.mitch.template.data.userprefs.UserPreferencesLocalDataSource
import com.mitch.template.data.userprefs.UserPreferencesSerializer
import com.mitch.template.domain.models.UserPreferences
import com.mitch.template.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.template.util.network.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json

class DefaultDependenciesProvider(
    private val context: Context
) : DependenciesProvider {

    override val networkMonitor: NetworkMonitor by lazy {
        ConnectivityManagerNetworkMonitor(
            context = context,
            ioDispatcher = ioDispatcher
        )
    }

    override val userSettingsRepository: UserSettingsRepository by lazy {
        DefaultUserSettingsRepository(
            userPreferencesLocalDataSource = UserPreferencesLocalDataSource(preferencesDataStore),
            languageLocalDataSource = LanguageLocalDataSource(context)
        )
    }

    override val ioDispatcher: CoroutineDispatcher by lazy {
        Dispatchers.IO
    }

    override val defaultDispatcher: CoroutineDispatcher by lazy {
        Dispatchers.Default
    }

    override val coroutineScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + defaultDispatcher)
    }

    override val jsonSerializer: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    override val database: TemplateDatabase by lazy {
        Room.databaseBuilder(
            context,
            TemplateDatabase::class.java,
            "template.db"
        ).build()
    }

    override val preferencesDataStore: DataStore<UserPreferences> by lazy {
        DataStoreFactory.create(
            serializer = UserPreferencesSerializer(ioDispatcher),
            scope = CoroutineScope(coroutineScope.coroutineContext + ioDispatcher)
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
    }
}