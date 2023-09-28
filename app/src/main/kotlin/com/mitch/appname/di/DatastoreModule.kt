package com.mitch.appname.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.mitch.appname.ProtoUserPreferences
import com.mitch.appname.data.local.datastore.user.preferences.ProtoUserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDatastore(
        @ApplicationContext context: Context,
        protoUserPreferencesSerializer: ProtoUserPreferencesSerializer,
    ): DataStore<ProtoUserPreferences> =
        DataStoreFactory.create(
            serializer = protoUserPreferencesSerializer,
            scope = CoroutineScope(SupervisorJob())
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}
