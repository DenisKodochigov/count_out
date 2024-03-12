package com.example.count_out.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.STOPWATCH_STATE
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.StopwatchState
import com.example.count_out.entity.StreamsWorkout
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.service.stopwatch.StopWatch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service()
{
    @Inject lateinit var stopWatch: StopWatch
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var playerWorkOut: PlayerWorkOut
    private lateinit var coroutineStopwatch: CoroutineScope
    private lateinit var coroutineSpeech: CoroutineScope
    private val pause = mutableStateOf(false)
    private val show = true

    val flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00"))
    val flowStateService: MutableStateFlow<StateWorkOut> = MutableStateFlow(StateWorkOut())

    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.onPause.name -> pauseWorkout()
            StopwatchState.onStop.name -> stopWatch.onStop()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    fun startWorkout(training: Training, streamsWorkout: StreamsWorkout){
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        notificationHelper.setPauseButton(this)
        coroutineStopwatch = CoroutineScope(Dispatchers.Default)
        coroutineSpeech = CoroutineScope(Dispatchers.Default)
        coroutineService(training, pause, streamsWorkout)
        stopWatch.onStart { countTime -> sendCountTime(countTime) }
        startForegroundService()
    }
    private fun sendCountTime(tick: TickTime){
        notificationHelper.updateNotification(hours = tick.hour, minutes = tick.min, seconds = tick.sec)
        coroutineStopwatch.launch{ flowTick.emit( tick )}
    }

    fun pauseWorkout(){
        pause.value = !pause.value
        stopWatch.onPause(pause)
        if (pause.value) notificationHelper.setContinueButton(this)
        else notificationHelper.setPauseButton(this)
    }
    fun stopWorkout(){
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopWatch.onStop()
        coroutineStopwatch.cancel()
        coroutineSpeech.cancel()
    }
    private fun coroutineService(
        training: Training,
        pause: MutableState<Boolean>,
        streamsWorkout: StreamsWorkout,
    ){
        coroutineSpeech.launch {
            try { playerWorkOut.playingWorkOut(training, pause, flowStateService) }
            catch ( e: InterruptedException){ e.printStackTrace() }
        }
    }
    private fun startForegroundService(){
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }


}

