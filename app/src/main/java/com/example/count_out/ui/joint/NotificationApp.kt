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
    private val channelId = "chanel_notification_count_out"
    private val channelName = "Sample Channel"

//    private fun requestPermission(permissionType: String, requestCode: Int) {
//        val permission = ContextCompat.checkSelfPermission( activityMy.applicationContext, permissionType)
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activityMy, arrayOf(permissionType), requestCode)
//        }
//    }
    fun sendNotification()
    {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle("Count_out")
            .setContentText("Service run")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        notificationManager.notify(1234, notificationBuilder.build())
        log(true, " send notification")
    }
    fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
            description = "This is a sample notification channel."
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
