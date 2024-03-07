package com.example.count_out.helpers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.count_out.MainActivity
import com.example.count_out.R
import com.example.count_out.domain.formatTime
import com.example.count_out.entity.Const.CANCEL_REQUEST_CODE
import com.example.count_out.entity.Const.CLICK_REQUEST_CODE
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_ID
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_NAME
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.RESUME_REQUEST_CODE
import com.example.count_out.entity.Const.STOPWATCH_STATE
import com.example.count_out.entity.Const.STOP_REQUEST_CODE
import com.example.count_out.entity.StopwatchState
import com.example.count_out.service.WorkoutService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(private val context: Context)
{
    private val flag = PendingIntent.FLAG_IMMUTABLE
    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val contentIntent by lazy {
        PendingIntent.getActivity(
            context,
            CLICK_REQUEST_CODE,
            Intent(context, MainActivity::class.java).apply {
                putExtra(STOPWATCH_STATE, StopwatchState.Started.name)
            },
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("00:00:00")
            .setOngoing(true)
            .setSound(null)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_timer)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }
    fun createChannel() =
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = NOTIFICATION_CHANNEL_DESCRIPTION
            setSound(null, null)
        }
//    fun getNotification(): Notification {
//        notificationManager.createNotificationChannel(createChannel())
//        return notificationBuilder.build()
//    }
//
//    fun updateNotification(notificationText: String? = null) {
//        notificationText?.let { notificationBuilder.setContentText(it) }
//        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
//    }
//    fun clickPendingIntent(context: Context): PendingIntent {
//        val clickIntent = Intent(context, MainActivity::class.java).apply {
//            putExtra(STOPWATCH_STATE, StopwatchState.Started.name)
//        }
//        return PendingIntent.getActivity(
//            context, CLICK_REQUEST_CODE, clickIntent, flag
//        )
//    }
    fun cancel(){
        notificationManager.cancel(NOTIFICATION_ID)
    }
    @SuppressLint("RestrictedApi")
    fun setStopButton(context: Context) {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0, NotificationCompat.Action(
                0, "Stop", stopPendingIntent(context)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun stopPendingIntent(context: Context): PendingIntent {
        val stopIntent = Intent(context, WorkoutService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.Stopped.name)
        }
        return PendingIntent.getService(
            context, STOP_REQUEST_CODE, stopIntent, flag
        )
    }
    private fun resumePendingIntent(context: Context): PendingIntent {
        val resumeIntent = Intent( context, WorkoutService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.Started.name)
        }
        return PendingIntent.getService(
            context, RESUME_REQUEST_CODE, resumeIntent, flag
        )
    }
    fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, WorkoutService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.Canceled.name)
        }
        return PendingIntent.getService(
            context, CANCEL_REQUEST_CODE, cancelIntent, flag
        )
    }
    fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID, notificationBuilder.setContentText(
                formatTime(hours = hours, minutes = minutes, seconds = seconds,)).build()
        )
    }
    fun triggerForegroundService(context: Context, action: String) {
        Intent(context, WorkoutService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }
    @SuppressLint("RestrictedApi")
    fun setResumeButton(context: Context) {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0, NotificationCompat.Action(
                0, "Resume", resumePendingIntent(context)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

}