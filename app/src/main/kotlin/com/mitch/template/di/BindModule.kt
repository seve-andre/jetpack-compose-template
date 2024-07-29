package com.mitch.template.di

import com.mitch.template.data.settings.DefaultUserSettingsRepository
import com.mitch.template.data.settings.UserSettingsRepository
import com.mitch.template.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.template.util.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    abstract fun bindsUserSettingsRepository(
        userSettingsRepositoryImpl: DefaultUserSettingsRepository
    ): UserSettingsRepository
}
