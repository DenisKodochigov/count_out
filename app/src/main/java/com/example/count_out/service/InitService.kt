package com.example.count_out.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.Training
import com.example.count_out.ui.joint.NotificationApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitService @Inject constructor( private val notificationApp: NotificationApp
){
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            val binderWorkoutService = binder as WorkoutService.LocalBinder
            mService = binderWorkoutService.getService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) { isBound  = false }
    }
    fun createService( bindService: (ServiceConnection)->Unit ){
        bindService(serviceConnection)
    }
    fun startWorkout(training: Training){
        notificationApp.sendNotification()
        if (isBound ) mService.startWorkout(training)
    }
    fun pauseWorkout(){
        if (isBound ) mService.pauseWorkout()
    }
    fun stopWorkout(){
        if (isBound ) mService.unbindService(serviceConnection)
        isBound  = false
    }
    companion object {
        lateinit var mService: WorkoutService
        var isBound : Boolean = false
    }
}
