package com.example.miodragmilosevic.kotlintest

import android.app.*
import android.content.Intent
import android.os.*
import android.widget.Toast
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class ForegroundService : Service() {

    private lateinit var serviceLooper: Looper

    //    private lateinit var mServiceHandler: ServiceHandler
    private lateinit var thread: HandlerThread
//    // Handler that receives messages from the thread

    private val CHANNEL_ID = "sampleAppChanell"
    private val ONGOING_NOTIFICATION_ID = 100


    override fun onCreate() {
        super.onCreate()
        thread = HandlerThread("ServiceStartArguments");
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
//        mServiceHandler = ServiceHandler(serviceLooper);




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

    class ServiceHandler() : Handler() {

        override fun handleMessage(msg: Message?) {


        }


    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
//        val msg = mServiceHandler.obtainMessage();
//        msg.arg1 = startId;
//        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
//        mServiceHandler.removeCallbacksAndMessages(null)
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
