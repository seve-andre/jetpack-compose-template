package com.mitch.template.di

import com.mitch.template.data.TemplateDatabase
import com.mitch.template.data.settings.UserSettingsRepository
import com.mitch.template.util.network.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface DependenciesProvider {
    val networkMonitor: NetworkMonitor
    val userSettingsRepository: UserSettingsRepository
    val ioDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
    val coroutineScope: CoroutineScope
    val database: TemplateDatabase
}
