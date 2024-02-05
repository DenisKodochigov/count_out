package com.example.count_out.ui.joint

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.count_out.MainActivity
import com.example.count_out.R
import com.example.count_out.ui.view_components.log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationApp @Inject constructor(val context: Context){
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notification: NotificationCompat.Builder = createNotification()
    private val channelId = "chanel_notification_count_out"
    private val channelName = "Sample Channel"
    private val notificationID = 111111

    fun sendNotification()
    {
        notificationManager.notify(notificationID, notification.build())
        log(true, "Send notification")
    }
    private fun createNotification(): NotificationCompat.Builder
    {
        log(true, "createNotification")
        val intent = Intent(this.context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this.context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle("Count_out")
            .setContentText("Service run")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

    }
    fun createNotificationChannel() {
        log(true, "createNotificationChannel")
        val notificationChannel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "This is a sample notification channel."
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }
    fun cancelNotification(){
        notificationManager.cancel(notificationID)
    }
}
