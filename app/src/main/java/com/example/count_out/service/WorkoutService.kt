package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.example.count_out.entity.Training
import com.example.count_out.helpers.ServiceThread
import com.example.count_out.ui.view_components.log
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {

    private var pauseService: Boolean = false
    private val serviceBinder = WorkoutServiceBinder()

    private val serviceThread = ServiceThread()
    private val delay = 1500L
    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }

    override fun onCreate() {
        super.onCreate()
        serviceThread.init()
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder
    }
    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        serviceThread.destroy()
    }
    fun startWorkout(training: Training){
        handlerService()
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){
        onDestroy()
        stopSelf()
    }

    private fun executorService(){
        val idThread = Random.nextInt()
        val executorService = Executors.newSingleThreadExecutor()
        executorService.execute{
            while(true){
                try {
                    Thread.sleep(delay)
                    bodyService()
                } catch ( e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }
    private fun handlerService(){
        serviceThread.run{
           while(true){
                try {
                    Thread.sleep(delay)
                    bodyService()
                } catch ( e: InterruptedException){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun bodyService(){
        if (! pauseService) log(true, "${getCurrentTime()}; serviceThread = ${serviceThread}; ")
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
}

