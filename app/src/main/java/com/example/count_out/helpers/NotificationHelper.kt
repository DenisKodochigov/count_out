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
import com.example.count_out.entity.Const.CLICK_REQUEST_CODE
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_ID
import com.example.count_out.entity.Const.NOTIFICATION_CHANNEL_NAME
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.SET_CONTENT_TITLE
import com.example.count_out.entity.Const.STOPWATCH_STATE
import com.example.count_out.entity.StopwatchState
import com.example.count_out.service.WorkoutService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(private val context: Context)
{
    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val pendingIntent by lazy {
        PendingIntent.getActivity(
            context,
            CLICK_REQUEST_CODE,
            Intent(context, MainActivity::class.java).apply {
                putExtra(STOPWATCH_STATE, StopwatchState.Started.name) },
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(SET_CONTENT_TITLE)
            .setContentText("00:00:00")
            .setOngoing(true)
            .setSound(null)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_timer)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(0, "Pause", onPauseAction(context))
            .addAction(0, "Stop", onStopAction(context))
            .setAutoCancel(true)
    }
    fun createChannel() {
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

    fun cancel(){
        notificationManager.cancel(NOTIFICATION_ID)
    }
    fun updateNotification(hours: String, minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID, notificationBuilder.setContentText(
                formatTime(hours = hours, minutes = minutes, seconds = seconds,)).build()
        )
    }
    @SuppressLint("RestrictedApi")
    fun setContinueButton(context: Context) {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0, NotificationCompat.Action(0, "Continue", onPauseAction(context)))
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
    @SuppressLint("RestrictedApi")
    fun setPauseButton(context: Context) {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0, NotificationCompat.Action(0, "Pause", onPauseAction(context)))
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
    private fun onPauseAction(context: Context): PendingIntent = PendingIntent.getService(
        context, 0,
        Intent(context, WorkoutService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.onPause.name) },
        PendingIntent.FLAG_IMMUTABLE )

    private fun onStopAction(context: Context): PendingIntent = PendingIntent.getService(
        context, 0,
        Intent(context, WorkoutService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.onStop.name) },
        PendingIntent.FLAG_IMMUTABLE )
}