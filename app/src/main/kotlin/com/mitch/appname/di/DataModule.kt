package com.mitch.appname.di

import com.mitch.appname.data.repo.AppLanguageRepoImpl
import com.mitch.appname.data.repo.UserPreferencesRepoImpl
import com.mitch.appname.domain.repo.AppLanguageRepo
import com.mitch.appname.domain.repo.UserPreferencesRepo
import com.mitch.appname.util.network.ConnectivityManagerNetworkMonitor
import com.mitch.appname.util.network.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

    @Binds
    @Singleton
    abstract fun bindsUserPreferencesRepo(
        userPreferencesRepo: UserPreferencesRepoImpl
    ): UserPreferencesRepo

    @Binds
    @Singleton
    abstract fun bindsAppLanguageRepo(
        appLanguageRepo: AppLanguageRepoImpl
    ): AppLanguageRepo
}
