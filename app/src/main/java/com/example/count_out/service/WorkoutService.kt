package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.Training
import com.example.count_out.service.player.PlayerWorkOut
import com.example.count_out.ui.view_components.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {

    private var pauseService: Boolean = false
    private val serviceBinder = WorkoutServiceBinder()
    private lateinit var coroutineScope: CoroutineScope
    @Inject lateinit var playerWorkOut: PlayerWorkOut

    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder
    }

    fun startWorkout(training: Training): MutableStateFlow<StateWorkOut>{
//        log(true, "WorkoutService.startWorkout.")
        coroutineScope = CoroutineScope(Dispatchers.Default)
        return coroutineService(training)
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){
        coroutineScope.cancel()
    }
    fun stateWorkout(): MutableStateFlow<StateWorkOut> {
        return MutableStateFlow(StateWorkOutDB())
    }
    private fun coroutineService(training: Training): MutableStateFlow<StateWorkOut>{
//        log(true, "WorkoutService.coroutineService.")
        var stateWorkOut: MutableStateFlow<StateWorkOut> = MutableStateFlow(StateWorkOutDB())
        coroutineScope.launch{
            try {
                stateWorkOut = playerWorkOut.stateWorkOut
                playerWorkOut.playingWorkOut(training)
                log(true, "stateWorkOut: ${stateWorkOut.value}")
            } catch ( e: InterruptedException){
                e.printStackTrace()
            }
        }
        return stateWorkOut
    }

    private suspend fun bodyService(training: Training){
//        if (! pauseService) log(true, "${getCurrentTime()}; count = ${count++}; service: $service ")
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
}

