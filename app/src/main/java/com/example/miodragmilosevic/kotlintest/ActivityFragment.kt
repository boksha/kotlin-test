package com.example.miodragmilosevic.kotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_activity.*

class ActivityFragment : Fragment() {


    /**
     * Initialize newInstance for passing paameters
     */
    companion object {
        fun newInstance(): ActivityFragment {
            var activityFragment = ActivityFragment()
            var args = Bundle()
            activityFragment.arguments = args
            return activityFragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_activity, container, false)
        val activity_title = rootView.findViewById<TextView>(R.id.activity_title)
        activity_title.text = getString(R.string.activity_title)
        return rootView
    }
}