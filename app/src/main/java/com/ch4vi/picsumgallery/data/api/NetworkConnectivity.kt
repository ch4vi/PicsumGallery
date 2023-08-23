package com.ch4vi.picsumgallery.data.api

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkConnectivity(
    private var application: Application
) {
    fun isOnline(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            }
        } ?: false
    }
}
