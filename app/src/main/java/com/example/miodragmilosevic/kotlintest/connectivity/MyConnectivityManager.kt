package com.example.miodragmilosevic.kotlintest.connectivity

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION

class MyConnectivityManager(val context : Context, val receiver : ConnectivityBroadcastReceiver) {

    fun start(){
        context.registerReceiver(receiver,IntentFilter(CONNECTIVITY_ACTION))
    }

    fun stop() {
        context.unregisterReceiver(receiver)
    }
}