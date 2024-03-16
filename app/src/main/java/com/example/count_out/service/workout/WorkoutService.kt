package com.example.count_out.service.workout

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.STOPWATCH_STATE
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.StopwatchState
import com.example.count_out.entity.TemplatePlayer
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
class WorkoutService @Inject constructor(): Service(), WorkOutAPI
{
    @Inject lateinit var stopWatch: StopWatch
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var playerWorkOut: PlayerWorkOut
    private lateinit var coroutineSpeech: CoroutineScope
    private val pause = mutableStateOf(false)

    override var training: MutableStateFlow<Training> = MutableStateFlow(TrainingDB() as Training)
    override lateinit var flowTick: MutableStateFlow<TickTime>
    override lateinit var flowStateService: MutableStateFlow<StateWorkOut>
    inner class WorkoutServiceBinder : Binder() { fun getService(): WorkoutService = this@WorkoutService }
    override fun onBind(p0: Intent?): IBinder = WorkoutServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.onPause.name -> pauseWorkout()
            StopwatchState.onStop.name -> stopWatch.onStop()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @SuppressLint("ForegroundServiceType")
    override suspend fun startWorkout(){
        flowTick = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00"))
        flowStateService = MutableStateFlow(StateWorkOut())
        startForegroundService()
        notificationHelper.setPauseButton(this)
        coroutineSpeech = CoroutineScope(Dispatchers.Default)
        playTraining()
        stopWatch.onStart { countTime -> sendCountTime(countTime) }
    }

    override fun pauseWorkout(){
        pause.value = !pause.value
        stopWatch.onPause(pause)
        if (pause.value) notificationHelper.setContinueButton(this)
        else notificationHelper.setPauseButton(this)
    }
    override fun stopWorkout(){
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopWatch.onStop()
        coroutineSpeech.cancel()
    }
    private fun sendCountTime(tick: TickTime){
        notificationHelper.updateNotification(hours = tick.hour, minutes = tick.min, seconds = tick.sec)
        flowTick.value = tick
    }
    private fun playTraining() {
        coroutineSpeech.launch {
            playerWorkOut.playingWorkOut(TemplatePlayer(training = training), pause, flowStateService)
        }
    }
    private fun startForegroundService(){
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
}

