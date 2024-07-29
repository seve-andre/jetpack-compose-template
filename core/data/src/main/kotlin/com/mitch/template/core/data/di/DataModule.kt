package com.mitch.template.core.data.di

import com.mitch.template.core.data.network.ConnectivityManagerNetworkMonitor
import com.mitch.template.core.data.settings.DefaultUserSettingsRepository
import com.mitch.template.core.domain.NetworkMonitor
import com.mitch.template.core.domain.UserSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BindModule {

    @Binds
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    abstract fun bindsUserSettingsRepository(
        userSettingsRepositoryImpl: DefaultUserSettingsRepository
    ): UserSettingsRepository
}
