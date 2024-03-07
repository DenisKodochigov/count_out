package com.example.count_out.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.count_out.entity.Const.ACTION_SERVICE_CANCEL
import com.example.count_out.entity.Const.ACTION_SERVICE_START
import com.example.count_out.entity.Const.ACTION_SERVICE_STOP
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.STOPWATCH_STATE
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.StopwatchState
import com.example.count_out.entity.Training
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.service.stopwatch.StopWatch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {
    private lateinit var coroutineScope: CoroutineScope
    @Inject lateinit var stopWatch: StopWatch
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var playerWorkOut: PlayerWorkOut

    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.Started.name -> {
                notificationHelper.setStopButton(this)
                startForegroundService()
                stopWatch.onStart { hours, minutes, seconds ->
                    notificationHelper.updateNotification(hours = hours, minutes = minutes, seconds = seconds)
                }
            }
            StopwatchState.Stopped.name -> {
                stopWatch.onStop()
                notificationHelper.setResumeButton(this)
            }
            StopwatchState.Canceled.name -> {
                stopWatch.onStop()
                stopWatch.cancel()
                stopForegroundService()
            }
        }
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    notificationHelper.setStopButton(this)
                    startForegroundService()
                    stopWatch.onStart { hours, minutes, seconds ->
                        notificationHelper.updateNotification(hours = hours, minutes = minutes, seconds = seconds)
                    }
                }
                ACTION_SERVICE_STOP -> {
                    stopWatch.onStop()
                    notificationHelper.setResumeButton(this)
                }
                ACTION_SERVICE_CANCEL -> {
                    stopWatch.onStop()
                    stopWatch.cancel()
                    stopForegroundService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        notificationHelper.createChannel()
        startForeground(NOTIFICATION_ID, notificationHelper.notificationBuilder.build())
    }
    private fun stopForegroundService() {
        notificationHelper.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    fun startWorkout(training: Training, stateService: (StateWorkOut)->Unit){
        coroutineService(training, stateService)
        stopWatch.onStart { hours, minutes, seconds ->
            notificationHelper.updateNotification(hours = hours, minutes = minutes, seconds = seconds)
        }
    }
    fun pauseWorkout(){

    }
    fun stopWorkout(){
        coroutineScope.cancel()
    }
    private fun coroutineService(training: Training, stateService: (StateWorkOut)->Unit){
        coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch{
            try {
                playerWorkOut.playingWorkOut(training, stateService)
            } catch ( e: InterruptedException){
                e.printStackTrace()
            }
        }
    }
}

