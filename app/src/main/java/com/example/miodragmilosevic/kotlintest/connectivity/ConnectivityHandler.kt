package com.example.miodragmilosevic.kotlintest.connectivity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

class ConnectivityHandler(val context : Context) {

    fun handleIntent(intent : Intent){
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        val isWiFi = activeNetwork?.type == ConnectivityManager.TYPE_WIFI
        val isMobile: Boolean = activeNetwork?.type == ConnectivityManager.TYPE_MOBILE
        Log.i("Miki","ConnectivityHandler handleIntent wifi : $isWiFi mobile : $isMobile is connected : $isConnected" )

        TODO("update changes to room")

    }
}
