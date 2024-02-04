package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {

    private var pauseService: Boolean = false
    private val serviceBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder //throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
    fun startWorkout(training: Training){
        while(!pauseService){
            runBlocking { delay(2000) }
            log(true, "${getCurrentTime()} Text function into service")
        }
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){

    }

    fun computeData(){
        var data = 0
        val executorService = Executors.newSingleThreadExecutor()
        executorService.execute{
            for (i in 0..99){
                data = i
                try {
                    Thread.sleep(5000)
                } catch ( e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
}