package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.Training
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {

    @Inject lateinit var speechManager: SpeechManager
    private var pauseService: Boolean = false
    private val serviceBinder = WorkoutServiceBinder()
    private lateinit var coroutineScope: CoroutineScope

    private val delay = 5000L
    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder
    }

    fun startWorkout(training: Training){
//        log(true, "WorkoutService.startWorkout.")
        coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineService(training)
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){
        coroutineScope.cancel()
    }
    private fun coroutineService(training: Training){
//        log(true, "WorkoutService.coroutineService.")
        coroutineScope.launch{
            try {
                bodyService(training)
            } catch ( e: InterruptedException){
                e.printStackTrace()
            }
        }
    }

    private suspend fun bodyService(training: Training){
        speechManager.speakOut(training.speech.soundBeforeStart)
        delay(delay)
        speechManager.speakOut(training.speech.soundAfterStart)
        delay(delay)
        speechManager.speakOut(training.speech.soundBeforeEnd)
        delay(delay)
        speechManager.speakOut(training.speech.soundAfterEnd)
//        if (! pauseService) log(true, "${getCurrentTime()}; count = ${count++}; service: $service ")
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
}

