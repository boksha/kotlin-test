package com.example.miodragmilosevic.kotlintest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_nav.*


class NavActivity : AppCompatActivity() {
    private lateinit var startServiceIntent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
//        navigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.drawer_activity -> {
//                    Log.i("Miki", "activity")
//                    val fragment = ActivityFragment.Companion.newInstance()
//                    addFragment(fragment)
//                    true
//                }
//                R.id.drawer_connectivity -> {
//                    Log.i("Miki", "connectivity")
//                    val fragment = ConnectivityFragment.Companion.newInstance()
//                    addFragment(fragment)
//                    true
//                }
//                R.id.drawer_geofence -> {
//                    Log.i("Miki", "geofence")
//                    val fragment = GeofenceFragment.Companion.newInstance()
//                    addFragment(fragment)
//                    true
//                }
//                else -> {
//                    Log.i("Miki", "something else")
//                    false
//
//                }
//
//            }
//        }
        val fragment = ActivityFragment.Companion.newInstance()
        addFragment(fragment)
        startServiceIntent = Intent(this,ForegroundService::class.java)
        ContextCompat.startForegroundService(this, startServiceIntent)
    }

    override fun onDestroy() {
        stopService(startServiceIntent)
        super.onDestroy()
    }
    /**
     * add/replace fragment in container [framelayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
//                .addToBackStack(fragment.javaClass.getSimpleName())
                .commit()
    }

}