package com.example.miodragmilosevic.kotlintest

import android.util.Log
import android.widget.Toast
import androidx.work.Worker

class TestWorker : Worker() {
    override fun doWork(): Result {
        Log.i("Miki","testworker");
        return Result.SUCCESS

}

}