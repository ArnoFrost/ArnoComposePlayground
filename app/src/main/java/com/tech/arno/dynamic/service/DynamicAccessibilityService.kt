package com.tech.arno.dynamic.service

import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.blankj.utilcode.util.ToastUtils
import com.tech.arno.R
import com.tech.arno.dynamic.component.DynamicWindow

class DynamicAccessibilityService : AccessibilityService() {
    companion object {
        var isReady = false
    }

    private var dynamicWindow: DynamicWindow? = null

    override fun onCreate() {
        isReady = true
        startMyOwnForeground()
        dynamicWindow = DynamicWindow(this).apply {
            init()
            showWindow()
        }
        ToastUtils.showShort("开始运行🏝️")
    }

    override fun onDestroy() {
        super.onDestroy()
        isReady = false
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


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }
}