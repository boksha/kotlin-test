package com.example.miodragmilosevic.kotlintest.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import com.example.miodragmilosevic.kotlintest.ForegroundService
import com.example.miodragmilosevic.kotlintest.ForegroundService.Companion.ACTION_CONNECTIVITY

class ConnectivityBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = Intent(context, ForegroundService::class.java)
        intent.action = ACTION_CONNECTIVITY
        if (context != null) {
            ContextCompat.startForegroundService(context, intent)
        }
    }
}