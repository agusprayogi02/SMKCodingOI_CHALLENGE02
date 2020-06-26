package id.agusprayogi02.pabarcovid19.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.agusprayogi02.pabarcovid19.MainActivity
import id.agusprayogi02.pabarcovid19.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = String::class.java.simpleName

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "From: " + p0.from)

        p0.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + p0.data)

            if (!p0.data.isNullOrEmpty()){
                val msg:String = p0.data["message"].toString()
                sendNotification(msg)
            }
        }

        p0.notification.let {
            if (it != null) {
                sendNotification(it.body!!)
            }
        }
    }

    override fun onNewToken(token : String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(msg: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.fcm_message))
            .setContentText(msg)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}