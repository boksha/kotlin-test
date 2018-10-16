package com.example.miodragmilosevic.kotlintest

import android.app.*
import android.content.Intent
import android.os.*
import android.widget.Toast
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.miodragmilosevic.kotlintest.activityrecognition.ActivityRecognitionHandler
import com.example.miodragmilosevic.kotlintest.activityrecognition.ActivityRecognitionManager
import com.example.miodragmilosevic.kotlintest.connectivity.ConnectivityBroadcastReceiver
import com.example.miodragmilosevic.kotlintest.connectivity.ConnectivityHandler
import com.example.miodragmilosevic.kotlintest.connectivity.MyConnectivityManager





class ForegroundService : Service() {
    
    companion object {
        const val ACTION_CONNECTIVITY = "om.example.miodragmilosevic.kotlintest.ACTION_CONNECTIVITY"
        const val ACTION_ACTIVITY_RECOGNITION = "om.example.miodragmilosevic.kotlintest.ACTION_ACTIVITY_RECOGNITION"
        const val ACTION_GEOFENCING = "om.example.miodragmilosevic.kotlintest.ACTION_GEOFENCING"
    }
    private val CHANNEL_ID = "sampleAppChanell"
    private val ONGOING_NOTIFICATION_ID = 100

    private lateinit var serviceLooper: Looper
    private lateinit var serviceHandler: ServiceHandler
    private lateinit var thread: HandlerThread
    private lateinit var connectivityHandler : ConnectivityHandler
    private lateinit var activityRecognitionHandler : ActivityRecognitionHandler
//    private lateinit var geofencingHandler : GeofencingHandler
    private lateinit var myConnectivityManager : MyConnectivityManager
    private lateinit var activityRecognitionManager : ActivityRecognitionManager
//    private lateinit var geofencingManager : GeofencingManager


    override fun onCreate() {
        super.onCreate()
        thread = HandlerThread("ServiceStartArguments");
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = ServiceHandler(serviceLooper);
        myConnectivityManager = MyConnectivityManager(application, ConnectivityBroadcastReceiver())
        connectivityHandler = ConnectivityHandler(application)
        myConnectivityManager.start()

        activityRecognitionHandler = ActivityRecognitionHandler()
        activityRecognitionManager = ActivityRecognitionManager()
        activityRecognitionManager.start()


        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         createNotificationChanel()
        }

        /*
         // TODO(developer). If targeting O, use the following code.
         if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
             mNotificationManager.startServiceInForeground(new Intent(this,
                     LocationUpdatesService.class), NOTIFICATION_ID, getNotification());
         } else {
             startForeground(NOTIFICATION_ID, getNotification());
         }
          */
        startForeground(ONGOING_NOTIFICATION_ID, getNotification());
    }

    inner class ServiceHandler(looper:Looper) : Handler(looper) {

        override fun handleMessage(msg: Message?) {
            val intent = msg?.obj
            if (intent is Intent){
                Log.i("Miki","handleMessage ")
                val action = intent.action
                when(action){
                    ACTION_CONNECTIVITY -> connectivityHandler.handleIntent(intent)
                    ACTION_ACTIVITY_RECOGNITION -> activityRecognitionHandler.handleIntent(intent)
                    else -> Log.i("Miki","Irelevant action")
                }
            }

        }


    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        val action : String? = intent?.action
        if (action != null){
            val msg = serviceHandler.obtainMessage();
            msg.obj = intent;
            msg.what = startId
            serviceHandler.sendMessage(msg);
        }


        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        myConnectivityManager.stop()
        activityRecognitionManager.stop()
        serviceHandler.removeCallbacksAndMessages(null)
        thread.quit()
        super.onDestroy()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val name = getString(R.string.app_name) + " channel";
        // Create the channel for the notification
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;

        val mChannel =
                NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

        // Set the Notification Channel for the Notification Manager.
        mNotificationManager.createNotificationChannel(mChannel);
    }


    /**
     * Returns the [NotificationCompat] used as part of the foreground service.
     */
    private fun getNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val builder = Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Title")
                    .setContentText("Text")
                    .setSmallIcon(R.drawable.ic_activity_menu)


            val notification = builder.build();
            return notification

        } else {

            val builder = NotificationCompat.Builder(this)
                    .setContentTitle("Title")
                    .setContentText("Text")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.ic_activity_menu)


            val notification = builder.build();
            return notification
        }
//        val builder = NotificationCompat.Builder(this)
//
//                .setContentText("Text")
//                .setContentTitle("Title")
//
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setSmallIcon(R.drawable.ic_activity_menu)
//
//
//        // Set the Channel ID for Android O.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            builder.setChannelId(CHANNEL_ID) // Channel ID
//        }
//
//        return builder.build()
    }
}

//}

/*
    private static final String CHANNEL_ID = "media_playback_channel";

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationManager
                mNotificationManager =
                (NotificationManager) mContext
                        .getSystemService(Context.NOTIFICATION_SERVICE);
        // The id of the channel.
        String id = CHANNEL_ID;
        // The user-visible name of the channel.
        CharSequence name = "Media playback";
        // The user-visible description of the channel.
        String description = "Media playback controls";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        mNotificationManager.createNotificationChannel(mChannel);
    }

    // You only need to create the channel on API 26+ devices
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
  createChannel();
}
NotificationCompat.Builder notificationBuilder =
       new NotificationCompat.Builder(mContext, CHANNEL_ID);
notificationBuilder
       .setStyle(
               new MediaStyle()
                       .setMediaSession(token)
                       .setShowCancelButton(true)
                       .setCancelButtonIntent(
                           MediaButtonReceiver.buildMediaButtonPendingIntent(
                               mContext, PlaybackStateCompat.ACTION_STOP)))
       .setColor(ContextCompat.getColor(mContext, R.color.notification_bg))
       .setSmallIcon(R.drawable.ic_stat_image_audiotrack)
       .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
       .setOnlyAlertOnce(true)
       .setContentIntent(createContentIntent())
       .setContentTitle(“Album”)
       .setContentText(“Artist”)
       .setSubText(“Song Name”)
       .setLargeIcon(MusicLibrary.getAlbumBitmap(mContext, description.getMediaId()))
       .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
               mService, PlaybackStateCompat.ACTION_STOP));
*
* */
