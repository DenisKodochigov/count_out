package com.example.count_out.service.workout

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.StateRunning
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.service.stopwatch.StopWatchObj
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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
    @Inject lateinit var playerWorkOut: PlayerWorkOut
    private lateinit var scopeSpeech: CoroutineScope
    private lateinit var scopeTick: CoroutineScope

    inner class WorkoutServiceBinder : Binder() { fun getService(): WorkoutService = this@WorkoutService }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(NOTIFICATION_EXTRA)) {
            StateRunning.Started.name -> continueWorkout()
            StateRunning.Paused.name -> pauseWorkout()
            StateRunning.Stopped.name -> stopWorkout()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    override fun startWorkout() {
        sendToUI = SendToUI()
        lg("startWorkout $sendToUI")
        sendToUI?.let {
            if (it.stateRunning.value == StateRunning.Stopped || it.stateRunning.value == StateRunning.Created){
                it.stateRunning.value = StateRunning.Started
                it.nextSet.value = sendToWork?.getSet(0)
                startForegroundService()
                getTick()
                playTraining()
            } else if (it.stateRunning.value == StateRunning.Paused) { continueWorkout() }
        }
    }
    override fun continueWorkout(){
        sendToUI?.let { it.stateRunning.value = StateRunning.Started }
        notificationHelper.setPauseButton()
    }
    override fun pauseWorkout() {
        sendToUI?.let { it.stateRunning.value = StateRunning.Paused }
        notificationHelper.setContinueButton()
    }
    override fun stopWorkout(){
        lg("stopWorkout")
        StopWatchObj.stop()
        sendToUI?.let { it.cancel() }
        sendToUI = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        scopeSpeech.cancel()
        scopeTick.cancel()
    }
    private fun getTick(){
        sendToUI?.let {
            StopWatchObj.start(it.stateRunning)
            scopeTick = CoroutineScope(Dispatchers.Default)
            scopeTick.launch {
                StopWatchObj.getTickTime().collect{ tick ->
                    notificationHelper.updateNotification(hours = tick.hour, minutes = tick.min, seconds = tick.sec)
                    it.flowTick.value = tick
                }
            }
        }
    }
    private fun playTraining() {
        scopeSpeech = CoroutineScope(Dispatchers.Default)
        sendToUI?.let { toUI->
            sendToWork?.let { toWork->
                scopeSpeech.launch {
                    playerWorkOut.playingWorkOut(toWork, toUI)
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


