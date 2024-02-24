package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.entity.Training
import com.example.count_out.service.player.PlayerWorkOut
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
    fun startWorkout(training: Training, stateService: MutableStateFlow<StateWorkOutDB>){
        coroutineService(training, stateService)
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){
        coroutineScope.cancel()
    }

    private fun coroutineService(training: Training, stateService: MutableStateFlow<StateWorkOutDB>){
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

