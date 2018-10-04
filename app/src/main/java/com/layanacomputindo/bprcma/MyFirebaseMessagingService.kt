package com.layanacomputindo.bprcma

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = "Service"
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage != null) {
            Log.e(TAG, "From: " + remoteMessage.getFrom()!!)
        }
        if (remoteMessage != null) {
            if (remoteMessage.getData().size > 0) {
                Log.e(TAG, "Notif contain data: " + remoteMessage.getData())
                sendNotification(remoteMessage)
            }
        }

        if (remoteMessage != null) {
            if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "Notif From FirebaseConsole when App in ForeGround: " + remoteMessage.getNotification()!!.body!!)
                sendNotification(remoteMessage)
            }
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
//        val b = Bundle()
//        val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
//        val rupiah = rupiahFormat.format(java.lang.Double.valueOf(remoteMessage.data["saldo"]))
//        b.putString("saldo", rupiah)

        val intent = Intent(this, InformasiKreditActivity::class.java)
//        intent.putExtras(b)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder

        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.setBigContentTitle("BPR-CMA")
        bigTextStyle.bigText(remoteMessage.data["message"])

        notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources,
                        R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(remoteMessage.data["message"])
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setStyle(bigTextStyle)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }
}