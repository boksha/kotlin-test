package com.example.miodragmilosevic.kotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_activity.*

class GeofenceFragment : Fragment() {


    /**
     * Initialize newInstance for passing paameters
     */
    companion object {
        fun newInstance(): GeofenceFragment {
            var geofenceFragment = GeofenceFragment()
            var args = Bundle()
            geofenceFragment.arguments = args
            return geofenceFragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_activity, container, false)
        val activity_title = rootView.findViewById<TextView>(R.id.activity_title)
        activity_title.text = getString(R.string.geofence_title)
        return rootView
    }
}