package com.mitch.template.core.domain

import kotlinx.coroutines.flow.Flow

/**
 * Network monitoring for reporting app connectivity status
 */
interface NetworkMonitor {
    /**
     * Exposes if app connectivity status is online
     */
    val networkInfo: Flow<NetworkInfo>
}

data class NetworkInfo(
    val isOnline: Boolean,
    val isOnWifi: Boolean
)
