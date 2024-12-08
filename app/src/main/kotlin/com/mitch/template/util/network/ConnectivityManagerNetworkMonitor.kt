package com.mitch.template.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class ConnectivityManagerNetworkMonitor(
    private val context: Context,
    ioDispatcher: CoroutineDispatcher
) : NetworkMonitor {
    override val networkInfo: Flow<NetworkInfo> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()
        if (connectivityManager == null) {
            channel.trySend(NetworkInfo(isOnline = false, isOnWifi = false))
            channel.close()
            return@callbackFlow
        }

        val callback = object : NetworkCallback() {
            private val networks = mutableSetOf<Network>()

            override fun onAvailable(network: Network) {
                networks += network
                channel.trySend(
                    NetworkInfo(
                        isOnline = true,
                        isOnWifi = connectivityManager.isOnWifi()
                    )
                )
                Timber.d("onAvailable: $networks with ${connectivityManager.isOnWifi()}")
            }

            override fun onLost(network: Network) {
                networks -= network
                channel.trySend(
                    NetworkInfo(
                        isOnline = networks.isNotEmpty(),
                        isOnWifi = connectivityManager.isOnWifi()
                    )
                )
                Timber.d("onLost: $networks with ${connectivityManager.isOnWifi()}")
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                channel.trySend(
                    NetworkInfo(
                        isOnline = networks.isNotEmpty(),
                        isOnWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    )
                )
                Timber.d("onCapabilitiesChanged for network $network")
            }
        }

        val request = Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)

        channel.trySend(
            NetworkInfo(
                isOnline = connectivityManager.isCurrentlyConnected(),
                isOnWifi = connectivityManager.isOnWifi()
            )
        )

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
        .flowOn(ioDispatcher)
        .conflate()

    private fun ConnectivityManager.isCurrentlyConnected(): Boolean {
        val networkCapabilities = this.networkCapabilities() ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun ConnectivityManager.isOnWifi(): Boolean {
        val networkCapabilities = this.networkCapabilities() ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    private fun ConnectivityManager.networkCapabilities(): NetworkCapabilities? {
        return this.activeNetwork?.let(::getNetworkCapabilities)
    }
}

@Composable
fun NetworkMonitor.networkInfoState(): State<NetworkInfo> {
    return produceState(initialValue = NetworkInfo(isOnline = false, isOnWifi = false)) {
        this@networkInfoState.networkInfo.collect { value = it }
    }
}

data class NetworkInfo(
    val isOnline: Boolean,
    val isOnWifi: Boolean
)
