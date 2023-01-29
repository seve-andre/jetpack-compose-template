package com.mitch.appname.di

import com.mitch.appname.data.local.datastore.user.preferences.UserPreferencesRepo
import com.mitch.appname.data.local.datastore.user.preferences.UserPreferencesRepoImpl
import com.mitch.appname.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.appname.util.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNetworkMonitor(
        connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    fun bindsUserPreferencesDataSource(
        userPreferencesRepo: UserPreferencesRepoImpl
    ): UserPreferencesRepo
}
