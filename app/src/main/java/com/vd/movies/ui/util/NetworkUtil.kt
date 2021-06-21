package com.vd.movies.ui.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NetworkUtil {
    fun isOnline(): Boolean
}

class NetworkUtilImp @Inject constructor( @ApplicationContext val mAppContext: Context): NetworkUtil  {
    override fun isOnline(): Boolean {
        val cm = mAppContext.getSystemService(ConnectivityManager::class.java)
        val an = cm.activeNetwork
        val nc  = cm.getNetworkCapabilities(an)
        return nc?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        } ?: false
    }
}
