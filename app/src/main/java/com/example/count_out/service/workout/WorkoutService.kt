package com.example.count_out.service.workout

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.service.stopwatch.StopWatchObj
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
    override var variablesOut: VariablesOutService = VariablesOutService()
    override var variablesIn: VariablesInService = VariablesInService()

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
        variablesOut.stateRunning.value.let {
            if (it == StateRunning.Stopped || it == StateRunning.Created){
                variablesOut.stateRunning.value = StateRunning.Started
                startForegroundService()
                getTick()
                playTraining()
            } else if (it == StateRunning.Paused) { continueWorkout() }
        }
    }
    override fun continueWorkout(){
        variablesOut.stateRunning.value = StateRunning.Started
        notificationHelper.setPauseButton()
    }
    override fun pauseWorkout() {
        variablesOut.stateRunning.value = StateRunning.Paused
        notificationHelper.setContinueButton()
    }
    override fun stopWorkout(){
        StopWatchObj.stop()
        variablesOut.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        scopeSpeech.cancel()
        scopeTick.cancel()
    }
    private fun getTick(){
        StopWatchObj.start(variablesOut.stateRunning)
        scopeTick = CoroutineScope(Dispatchers.Default)
        scopeTick.launch {
            StopWatchObj.getTickTime().collect{ tick ->
                notificationHelper.updateNotification(hours = tick.hour, minutes = tick.min, seconds = tick.sec)
                variablesOut.flowTick.value = tick
            }
        }
    }
    private fun playTraining() {
        scopeSpeech = CoroutineScope(Dispatchers.Default)
        scopeSpeech.launch {
            playerWorkOut.playingWorkOut(variablesIn, variablesOut)
            stopWorkout()
        }
    }
    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
}


