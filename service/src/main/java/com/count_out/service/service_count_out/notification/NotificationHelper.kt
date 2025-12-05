package com.count_out.service.service_count_out.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.count_out.app.MainActivity
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.router.DataForNotification
import com.count_out.service.R
import com.count_out.service.service_count_out.CountOutService
import com.count_out.service.service_count_out.models.ConstVal
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(private val context: Context)
{
    val setContentTitle = "COUNT_OUT"

    val notificationExtra = "WORKOUT_NOTIFICATION_EXTRA"
    val notificationChannelId = "WORKOUT_NOTIFICATION_ID"
    val notificationChannelName = "WORKOUT_NOTIFICATION"
    val notificationChannelDescription = "WORKOUT_CHANNEL_DESCRIPTION"

    val startRequestCode = 100
    val pauseRequestCode = 101
    val stopRequestCode = 102
    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var pendingIntentA = PendingIntent.getActivity(
        context, 0,
        Intent(context, MainActivity::class.java), PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    private val builderPause: NotificationCompat.Builder = NotificationCompat.Builder(context, notificationChannelId)
        .setContentTitle(setContentTitle)
        .setContentText(contextText(DataForNotification()))
//        .setOngoing(false)
        .setContentIntent(pendingIntentA)
        .setSmallIcon(R.drawable.ic_timer)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(0, "  Pause ", intentAction(RunningState.Paused.name, pauseRequestCode))
        .addAction(0, "Stop", intentAction(RunningState.Stopped.name, stopRequestCode))
        .setAutoCancel(true)
    private val builderContinue: NotificationCompat.Builder = NotificationCompat.Builder(context, notificationChannelId)
        .setContentTitle(setContentTitle)
        .setContentText(contextText(DataForNotification()))
        .setOngoing(false)
        .setContentIntent(pendingIntentA)
        .setSmallIcon(R.drawable.ic_timer)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(0, "Continue", intentAction(RunningState.Started.name, startRequestCode))
        .addAction(0, "Stop", intentAction(RunningState.Stopped.name, stopRequestCode))
        .setAutoCancel(true)
    private val builderStart: NotificationCompat.Builder = NotificationCompat.Builder(context, notificationChannelId)
        .setContentTitle(setContentTitle)
        .setContentText(contextText(DataForNotification()))
        .setOngoing(false)
        .setContentIntent(pendingIntentA)
        .setSmallIcon(R.drawable.ic_timer)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(0, "Start", intentAction(RunningState.Started.name, startRequestCode))
        .setAutoCancel(true)
    fun createChannel() {
        val channel = NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            NotificationManager.IMPORTANCE_LOW
        ).apply { description = notificationChannelDescription }
        manager.createNotificationChannel(channel)
    }
    fun build() = builderStart.build()
    fun cancel(){ manager.cancel(ConstVal.NOTIFICATION_ID) }
    fun updateNotification(data: DataForNotification?, state: RunningState) {
        data?.let {
            when(state){
                RunningState.Started->manager
                    .notify(ConstVal.NOTIFICATION_ID, builderPause.setContentText(contextText(it)).build())
                RunningState.Paused->manager
                    .notify(ConstVal.NOTIFICATION_ID, builderContinue.setContentText(contextText(it)).build())
                RunningState.Stopped->manager
                    .notify(ConstVal.NOTIFICATION_ID, builderStart.setContentText(contextText(it)).build())
                RunningState.Binding -> {}
            }
        }
    }
    private fun contextText(data: DataForNotification) = "${data.hours}:${data.minutes}:${data.seconds}" +
            "    ${data.heartRate}    " + if (data.enableLocation) "GPS YES" else "GPS NO"

    private fun intentAction(value: String, code: Int): PendingIntent =
        PendingIntent.getService(context, code,
            Intent(context, CountOutService::class.java).apply { putExtra(notificationExtra, value ) },
            PendingIntent.FLAG_IMMUTABLE
        )
    fun channelExist(): Boolean{
        return if (manager.getNotificationChannel(ConstVal.NOTIFICATION_ID.toString()) != null) {
            manager.getNotificationChannel(ConstVal.NOTIFICATION_ID.toString()).importance !=
                    NotificationManager.IMPORTANCE_NONE
        } else false
    }
}