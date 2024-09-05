package com.example.count_out.helpers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.count_out.R
import com.example.count_out.domain.formatTime
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_ID
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_NAME
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.PAUSE_REQUEST_CODE
import com.example.count_out.entity.Const.SET_CONTENT_TITLE
import com.example.count_out.entity.Const.START_REQUEST_CODE
import com.example.count_out.entity.Const.STOP_REQUEST_CODE
import com.example.count_out.entity.RunningState
import com.example.count_out.service.workout.WorkoutService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(private val context: Context)
{
    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    private val pendingIntent by lazy { intentAction( RunningState.Started.name, START_REQUEST_CODE)}

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(SET_CONTENT_TITLE)
            .setContentText("00:00:00")
            .setOngoing(true)
            .setSound(null)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_timer)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(0, "Pause", intentAction(RunningState.Paused.name, PAUSE_REQUEST_CODE))
            .addAction(0, "Stop", intentAction(RunningState.Stopped.name, STOP_REQUEST_CODE))
            .setAutoCancel(true)
    }
    fun createChannel() {
//        lg("createChannel")
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = NOTIFICATION_CHANNEL_DESCRIPTION
            setSound(null, null)
        }
        notificationManager.createNotificationChannel(channel)
    }
    fun build() = notificationBuilder.build()
    fun cancel(){ notificationManager.cancel(NOTIFICATION_ID) }
    fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID, notificationBuilder.setContentText(
                formatTime(hours = hours, min = minutes, sec = seconds,)).build()
        )
    }

    @SuppressLint("RestrictedApi")
    fun setContinueButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0, NotificationCompat.Action(0, "Continue", intentAction(RunningState.Started.name, START_REQUEST_CODE)))
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
    @SuppressLint("RestrictedApi")
    fun setPauseButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(0,
            NotificationCompat.Action(0, "Pause", intentAction(RunningState.Paused.name, PAUSE_REQUEST_CODE)))
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun intentAction(value: String, code: Int):PendingIntent =
        PendingIntent.getService(context, code,
            Intent(context, WorkoutService::class.java).apply { putExtra(NOTIFICATION_EXTRA, value ) },
            PendingIntent.FLAG_IMMUTABLE
        )


    fun channelExist(): Boolean{
        return if (notificationManager.getNotificationChannel(NOTIFICATION_ID.toString()) != null) {
            notificationManager.getNotificationChannel(NOTIFICATION_ID.toString()).importance !=
                    NotificationManager.IMPORTANCE_NONE
        } else false
    }
}
