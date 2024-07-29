package com.mitch.template.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.mitch.template.core.datastore.userprefs.UserPreferencesSerializer
import com.mitch.template.core.domain.models.UserPreferences
import com.mitch.template.core.util.di.ApplicationScope
import com.mitch.template.core.util.di.Dispatcher
import com.mitch.template.core.util.di.TemplateDispatcher.Io
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatastoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDatastore(
        @ApplicationContext context: Context,
        @Dispatcher(Io) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher)
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}
