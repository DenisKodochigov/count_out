package com.example.count_out.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
    private val pause = mutableStateOf(false)
    @Inject lateinit var stopWatch: StopWatch
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var playerWorkOut: PlayerWorkOut

    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.onPause.name -> pauseWorkout()
            StopwatchState.onStop.name -> stopWatch.onStop()
        }
//        intent?.action.let {
//            when (it) {
//                ACTION_SERVICE_START -> {
//                    notificationHelper.setStopButton(this)
//                    startForegroundService()
//                    stopWatch.onStart { hours, minutes, seconds ->
//                        notificationHelper.updateNotification(hours = hours, minutes = minutes, seconds = seconds)
//                    }
//                }
//                ACTION_SERVICE_STOP -> {
//                    stopWatch.onStop()
//                    notificationHelper.setResumeButton(this)
//                }
//                ACTION_SERVICE_CANCEL -> {
//                    stopWatch.onStop()
//                    stopWatch.cancel()
//                    stopForegroundService()
//                }
//            }
//        }
        return super.onStartCommand(intent, flags, startId)
    }

//    private fun stopForegroundService() {
//        notificationHelper.cancel()
//        stopForeground(STOP_FOREGROUND_REMOVE)
//        stopSelf()
//    }

    @SuppressLint("ForegroundServiceType")
    fun startWorkout(training: Training, stateService: (StateWorkOut)->Unit){
        notificationHelper.createChannel()
        notificationHelper.setPauseButton(this)
        coroutineService(training, pause, stateService)
        stopWatch.onStart { hours, minutes, seconds ->
            notificationHelper.updateNotification(hours = hours, minutes = minutes, seconds = seconds)
        }
        startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
    fun pauseWorkout(){
        pause.value = !pause.value
        stopWatch.onPause(pause)
        if (pause.value) notificationHelper.setContinueButton(this)
        else notificationHelper.setPauseButton(this)
    }
    fun stopWorkout(){
        notificationHelper.cancel()
        stopWatch.onStop()
        stopForeground(STOP_FOREGROUND_REMOVE)
        coroutineScope.cancel()
    }
    private fun coroutineService(
        training: Training,
        pause: MutableState<Boolean>,
        stateService: (StateWorkOut)->Unit)
    {
        coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch{
            try {
                playerWorkOut.playingWorkOut(training, pause, stateService)
            } catch ( e: InterruptedException){
                e.printStackTrace()
            }
        }
    }
}

