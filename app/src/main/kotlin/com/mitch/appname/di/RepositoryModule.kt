package com.mitch.appname.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // see example -> https://github.com/philipplackner/StockMarketApp/blob/final/app/src/main/java/com/plcoding/stockmarketapp/di/RepositoryModule.kt
}
