package com.example.dream.wedding

import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessengerService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("William", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            val rm: RemoteMessage.Notification = message.notification!!
            pushNotification(rm.title, rm.body)
        }
    }

    private fun pushNotification(title: String?, body: String?) {
        val nm: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelID = "push_notification"

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Dream Wedding") //     .setPriority(Notification.PRIORITY_MAX)
            .setContentTitle(title)
            .setContentText(body)
            .setContentInfo("Info")
        nm.notify( /*notification id*/1, notificationBuilder.build())
    }
}