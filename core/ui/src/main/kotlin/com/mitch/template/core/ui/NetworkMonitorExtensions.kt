package com.mitch.template.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.mitch.template.core.domain.NetworkInfo
import com.mitch.template.core.domain.NetworkMonitor

@Composable
fun NetworkMonitor.networkInfoState(): State<NetworkInfo> {
    return produceState(initialValue = NetworkInfo(isOnline = false, isOnWifi = false)) {
        this@networkInfoState.networkInfo.collect { value = it }
    }
}
