package com.example.myapplication

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.ui.MainActivity
import com.example.myapplication.utils.Constants
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {
    private val TAG = "MyFCM"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.data.getValue("open")
        message.data?.let {it->
            showNotification(it.getValue("open"))
        }

    }

    @SuppressLint("MissingPermission")
    private fun showNotification(data: String) {
        // Create an explicit intent for an Activity in your app.
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra(Constants.OPEN_TAB_FROM_NOTIFICATION, data)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, getString(R.string.idrip_notification_channel_id))
            .setSmallIcon(R.drawable.star)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that fires when the user taps the notification.
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)){
            notify(123, builder.build())
        }
    }
}