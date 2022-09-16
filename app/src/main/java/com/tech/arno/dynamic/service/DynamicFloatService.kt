package com.tech.arno.dynamic.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.tech.arno.R
import com.tech.arno.dynamic.DynamicWindow

class DynamicFloatService : LifecycleService() {
    private var dynamicWindow: DynamicWindow? = null

    override fun onCreate() {
        super.onCreate()
        startMyOwnForeground()
        dynamicWindow = DynamicWindow(this).apply {
            init()
            showWindow()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        dynamicWindow?.destroy()
    }

    private fun startMyOwnForeground() {
        val NOTIFICATION_CHANNEL_ID = "com.arno.tech"
        val channelName = "Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_MIN
        )
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Service running")
            .setContentText("Displaying over other apps") // this is important, otherwise the notification will show the way
            // you want i.e. it will show some default notification
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }
}