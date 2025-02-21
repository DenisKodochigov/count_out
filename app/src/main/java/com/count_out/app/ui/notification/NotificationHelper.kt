package com.count_out.app.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.count_out.app.MainActivity
import com.count_out.app.R
import com.count_out.app.domain.router.models.DataForNotification
import com.count_out.app.entity.Const.NOTIFICATION_CHANNEL_DESCRIPTION
import com.count_out.app.entity.Const.NOTIFICATION_CHANNEL_ID
import com.count_out.app.entity.Const.NOTIFICATION_CHANNEL_NAME
import com.count_out.app.entity.Const.NOTIFICATION_EXTRA
import com.count_out.app.entity.Const.NOTIFICATION_ID
import com.count_out.app.entity.Const.PAUSE_REQUEST_CODE
import com.count_out.app.entity.Const.SET_CONTENT_TITLE
import com.count_out.app.entity.Const.START_REQUEST_CODE
import com.count_out.app.entity.Const.STOP_REQUEST_CODE
import com.count_out.app.entity.RunningState
import com.count_out.app.services.count_out.CountOutService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotificationHelper @Inject constructor(private val context: Context)
{
    private val manager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    private var pendingIntentA = PendingIntent.getActivity(
        context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    private val builderPause: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(SET_CONTENT_TITLE)
        .setContentText(contextText(DataForNotification()))
//        .setOngoing(false)
        .setContentIntent(pendingIntentA)
        .setSmallIcon(R.drawable.ic_timer)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(0, "  Pause ", intentAction(RunningState.Paused.name, PAUSE_REQUEST_CODE))
        .addAction(0, "Stop", intentAction(RunningState.Stopped.name, STOP_REQUEST_CODE))
        .setAutoCancel(true)
    private val builderContinue: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(SET_CONTENT_TITLE)
        .setContentText(contextText(DataForNotification()))
        .setOngoing(false)
        .setContentIntent(pendingIntentA)
        .setSmallIcon(R.drawable.ic_timer)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(0, "Continue", intentAction(RunningState.Started.name, START_REQUEST_CODE))
        .addAction(0, "Stop", intentAction(RunningState.Stopped.name, STOP_REQUEST_CODE))
        .setAutoCancel(true)
    private val builderStart: NotificationCompat.Builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentTitle(SET_CONTENT_TITLE)
        .setContentText(contextText(DataForNotification()))
        .setOngoing(false)
        .setContentIntent(pendingIntentA)
        .setSmallIcon(R.drawable.ic_timer)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(0, "Start", intentAction(RunningState.Started.name, START_REQUEST_CODE))
        .setAutoCancel(true)
    fun createChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        ).apply { description = NOTIFICATION_CHANNEL_DESCRIPTION }
        manager.createNotificationChannel(channel)
    }
    fun build() = builderStart.build()
    fun cancel(){ manager.cancel(NOTIFICATION_ID) }
    fun updateNotification(data: DataForNotification?, state: RunningState) {
        data?.let {
            when(state){
                RunningState.Started->manager
                    .notify(NOTIFICATION_ID, builderPause.setContentText(contextText(it)).build())
                RunningState.Paused->manager
                    .notify(NOTIFICATION_ID, builderContinue.setContentText(contextText(it)).build())
                RunningState.Stopped->manager
                    .notify(NOTIFICATION_ID, builderStart.setContentText(contextText(it)).build())
                RunningState.Binding -> {}
            }
        }
    }
    private fun contextText(data: DataForNotification) = "${data.hours}:${data.minutes}:${data.seconds}" +
            "    ${data.heartRate}    " + if (data.enableLocation) "GPS YES" else "GPS NO"

    private fun intentAction(value: String, code: Int):PendingIntent =
        PendingIntent.getService(context, code,
            Intent(context, CountOutService::class.java).apply { putExtra(NOTIFICATION_EXTRA, value ) },
            PendingIntent.FLAG_IMMUTABLE
        )
    fun channelExist(): Boolean{
        return if (manager.getNotificationChannel(NOTIFICATION_ID.toString()) != null) {
            manager.getNotificationChannel(NOTIFICATION_ID.toString()).importance !=
                    NotificationManager.IMPORTANCE_NONE
        } else false
    }
}
