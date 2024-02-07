package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {

    private var pauseService: Boolean = false
    private val serviceBinder = WorkoutServiceBinder()
    private lateinit var coroutineScope: CoroutineScope
    private var count = 0

    private val delay = 1500L
    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder
    }

    fun startWorkout(training: Training){
        coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineService()
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){
        coroutineScope.cancel()
    }
    private fun coroutineService(){
        coroutineScope.launch{
            while(true){
                try {
                    delay(delay)
                    bodyService()
                } catch ( e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun bodyService(){
        if (! pauseService) log(true, "${getCurrentTime()}; count = ${count++} ")
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
}

