package com.mitch.appname.di

import com.mitch.appname.data.repository.UserSettingsRepositoryImpl
import com.mitch.appname.domain.repository.UserSettingsRepository
import com.mitch.appname.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.appname.util.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    abstract fun bindsUserSettingsRepository(
        userSettingsRepositoryImpl: UserSettingsRepositoryImpl
    ): UserSettingsRepository
}
