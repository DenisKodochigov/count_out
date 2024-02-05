package com.example.count_out.service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.widget.Toast
import com.example.count_out.entity.Training
import com.example.count_out.ui.joint.NotificationApp
import com.example.count_out.ui.view_components.log
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutService @Inject constructor(): Service() {
    @Inject lateinit var notificationApp: NotificationApp
    private var pauseService: Boolean = false
    private val serviceBinder = WorkoutServiceBinder()
    private lateinit var handlerThread:HandlerThread
    private lateinit var handler: Handler
    private val delay = 1500L
    inner class WorkoutServiceBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }

    override fun onCreate() {
        super.onCreate()
        notificationApp.createNotificationChannel()
        val handlerThreadName = "workout_thread"
        handlerThread = HandlerThread(handlerThreadName)
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder //throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        handler.removeCallbacksAndMessages(null)
        handler.looper.quit()
        handlerThread.quit()
        notificationApp.cancelNotification()
    }
    fun startWorkout(training: Training){
        notificationApp.sendNotification()
        handlerService()
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
//    fun stopWorkout(){
//
//    }

//    private fun executorService(){
//        val idThread = Random.nextInt()
//        val executorService = Executors.newSingleThreadExecutor()
//        executorService.execute{
//            while(true){
//                try {
//                    Thread.sleep(delay)
//                    bodyService()
//                } catch ( e: InterruptedException){
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
    private fun handlerService(){
        handler.post{
           while(true){
                try {
                    Thread.sleep(delay)
                    bodyService()
                } catch ( e: InterruptedException){
                    e.printStackTrace()
                }
            }
//            while (true) {
//                runBlocking {
//                    delay(delay)
//                    bodyService()
//                }
//            }
        }
    }
    private fun bodyService(){
        if (! pauseService) log(true, "Body workout service ${getCurrentTime()}; pauseService = $pauseService")
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
}