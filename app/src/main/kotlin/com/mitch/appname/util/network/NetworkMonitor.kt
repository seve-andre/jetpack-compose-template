package com.mitch.appname.util.network

import kotlinx.coroutines.flow.Flow

/**
 * Network monitoring for reporting app connectivity status
 */
interface NetworkMonitor {
    /**
     * Exposes if app connectivity status is online
     */
    val isOnline: Flow<Boolean>
}
