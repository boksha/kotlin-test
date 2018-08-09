package com.example.miodragmilosevic.kotlintest

import android.arch.lifecycle.LiveData
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkStatus
import androidx.work.OneTimeWorkRequestBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var request: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title_tv.setText("Miki extension")
        toastButton.setOnClickListener {
            val workManager = WorkManager.getInstance()
            request = OneTimeWorkRequestBuilder<TestWorker>().build()
            workManager.enqueue(request)
            val status : LiveData<WorkStatus> = workManager.getStatusById(request.getId());
//            status.observe(this,);
//            Toast.makeText(this,"Miki Toast",Toast.LENGTH_LONG ).show()
        }

    }

    /*
    *

You can also use the id for cancellation:
 WorkRequest request = new OneTimeWorkRequest.Builder(FooWorker.class).build();
 workManager.enqueue(request);
 workManager.cancelWorkById(request.getId());

 WorkManager.getInstance().getStatusById(compressionWork.id)
                .observe(lifecycleOwner, Observer { workStatus ->
                    // Do something with the status
                    if (workStatus != null && workStatus.state.isFinished) {
                        // ...
                    }
                })

                // Create a Constraints that defines when the task should run
val myConstraints = Constraints.Builder()
        .setRequiresDeviceIdle(true)
        .setRequiresCharging(true)
        // Many other constraints are available, see the
        // Constraints.Builder reference
        .build()

val compressionWork = OneTimeWorkRequestBuilder<CompressWorker>()
        .setConstraints(myConstraints)
        .build()
    * */
}
