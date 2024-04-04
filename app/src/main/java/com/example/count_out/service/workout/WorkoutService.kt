package com.example.count_out.service.workout

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.STOPWATCH_STATE
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.StopwatchState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.service.stopwatch.StopWatch
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
    override var variablesOut: VariablesOutService = VariablesOutService()
    override var variablesIn: VariablesInService = VariablesInService()

    @Inject lateinit var stopWatch: StopWatch
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var playerWorkOut: PlayerWorkOut
    private lateinit var scopeSpeech: CoroutineScope

    inner class WorkoutServiceBinder : Binder() { fun getService(): WorkoutService = this@WorkoutService }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.Started.name -> continueWorkout()
            StopwatchState.Paused.name -> pauseWorkout()
            StopwatchState.Stopped.name -> stopWatch.onStop()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    override fun startWorkout() {
        variablesOut.startTime = System.currentTimeMillis()
        if (variablesOut.stateRunning.value == StateRunning.Pause){
            continueWorkout()
        } else if (variablesOut.stateRunning.value == StateRunning.Stopped ||
                variablesOut.stateRunning.value == StateRunning.Created)
        {
            startForegroundService()
            variablesOut.stateRunning.value = StateRunning.Started
            scopeSpeech = CoroutineScope(Dispatchers.Default)
            notificationHelper.setPauseButton(this)
            stopWatch.onStart(variablesOut.stateRunning) { countTime -> sendCountTime(countTime) }
            playTraining()
        }
    }
    override fun continueWorkout(){
        variablesOut.stateRunning.value = StateRunning.Started
        notificationHelper.setContinueButton(this)
    }
    override fun pauseWorkout() {
        lg("notification pause")
        variablesOut.stateRunning.value = StateRunning.Pause
        notificationHelper.setContinueButton(this)
    }
    override fun stopWorkout(){
        stopWatch.onStop()
        variablesOut.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        scopeSpeech.cancel()
    }
    private fun sendCountTime(tick: TickTime){
        notificationHelper.updateNotification(hours = tick.hour, minutes = tick.min, seconds = tick.sec)
        variablesOut.flowTick.value = tick
    }
    private fun playTraining() {
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


