package com.example.count_out.service.workout

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.service.stopwatch.StopWatchObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service(), WorkOutAPI
{
    override var sendToUI: SendToUI? = null
    override var sendToWork: SendToWorkService? = null

    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var speechManager: SpeechManager
    @Inject lateinit var playerWorkOut: PlayerWorkOut
    @Inject lateinit var messageApp: MessageApp

    inner class WorkoutServiceBinder : Binder() { fun getService(): WorkoutService = this@WorkoutService }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(NOTIFICATION_EXTRA)) {
            RunningState.Started.name -> continueWorkout()
            RunningState.Paused.name -> pauseWorkout()
            RunningState.Stopped.name -> stopWorkout()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    override fun startWorkout() {
        if (sendToUI == null) { sendToUI = SendToUI() }
        sendToUI?.let {
            if (it.runningState.value == RunningState.Stopped){
                messageApp.messageApi("Start WorkOut")
                it.runningState.value = RunningState.Started
                it.nextSet.value = sendToWork?.getSet(0)
                startForegroundService()
                speechManager.init {
                    getTick()
                    playTraining() }
            } else if (it.runningState.value == RunningState.Paused) { continueWorkout() }
        }
    }
    override fun continueWorkout(){
        sendToUI?.let { it.runningState.value = RunningState.Started }
        notificationHelper.setPauseButton()
    }
    override fun pauseWorkout() {
        sendToUI?.let { it.runningState.value = RunningState.Paused }
        notificationHelper.setContinueButton()
    }
    override fun stopWorkout(){
        messageApp.messageApi("Stop WorkOut")
        StopWatchObj.stop()
        speechManager.stopTts()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        sendToUI?.cancel()
        sendToUI = null
        sendToWork = null
    }
    fun stopWorkoutCommand(){
        messageApp.messageApi("Command Stop WorkOut")
        sendToUI?.let { it.runningState.value = RunningState.Stopped }
    }
    private fun getTick(){
        sendToUI?.let {
            StopWatchObj.start(it.runningState)
            CoroutineScope(Dispatchers.Default).launch {
                StopWatchObj.getTickTime().collect{ tick ->
                    notificationHelper.updateNotification(hours = tick.hour, minutes = tick.min, seconds = tick.sec)
                    it.flowTick.value = tick
                }
            }
        }
    }
    private fun playTraining() {
        sendToUI?.let { toUI->
            sendToWork?.let { toWork->
                CoroutineScope(Dispatchers.Default).launch {
                    messageApp.messageApi("Begin training")
                    playerWorkOut.playingWorkOut(toWork, toUI)
                    messageApp.messageApi("End training")
                    stopWorkout()
                }
            }
        }
    }
    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
}


