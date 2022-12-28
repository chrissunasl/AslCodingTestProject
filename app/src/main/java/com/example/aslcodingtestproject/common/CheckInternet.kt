package com.example.aslcodingtestproject.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import timber.log.Timber

// check whether user have connect internet
object CheckInternet {
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Timber.tag("Internet").i("NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Timber.tag("Internet").i("NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Timber.tag("Internet").i("NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}