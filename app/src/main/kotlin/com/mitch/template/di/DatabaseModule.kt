package com.mitch.template.di

import android.content.Context
import androidx.room.Room
import com.mitch.template.data.TemplateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesTemplateDatabase(@ApplicationContext appContext: Context): TemplateDatabase {
        return Room.databaseBuilder(
            appContext,
            TemplateDatabase::class.java,
            "template.db"
        ).build()
    }
}
