package com.example.count_out.service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitService @Inject constructor(
){
    var mService: WorkoutService? = null
    var isBound : Boolean = false
    var bind:(ServiceConnection)->Unit = {}
    var unbind:(ServiceConnection)->Unit = {}

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            val binderWorkoutService = binder as WorkoutService.WorkoutServiceBinder
            mService = binderWorkoutService.getService()
            isBound  = true
            log(true, "onServiceConnected. isBound = $isBound")
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mService = null
            isBound  = false }
    }
//    fun createService(){
//        bind(serviceConnection)
//    }
    fun startWorkout(training: Training){
        log(true, "Start workout service. isBound = $isBound")
        if (isBound ) {
            mService?.startWorkout(training)
        }
    }
    fun pauseWorkout(){
        log(true, "Pause workout service. isBound = $isBound")
        if (isBound ) mService?.pauseWorkout()
    }
//    fun unbindService(){
//        log(true, "Stop workout service. isBound = $isBound")
//        if (isBound ) unbind(serviceConnection)
//        isBound  = false
//    }
    fun stopWorkout(){
        log(true, "Stop workout service. isBound = $isBound")
        mService?.onDestroy()
        isBound  = false
    }
}
