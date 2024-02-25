package com.example.count_out.service

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.data.room.tables.StateWorkOutDB
import com.example.count_out.entity.Training
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor( val context: Context
){
    var mService: WorkoutService? = null
    var isBound : Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            val binderService = binder as WorkoutService.WorkoutServiceBinder
            mService = binderService.getService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mService = null
            isBound  = false
        }
    }
    fun startWorkout(training: Training, stateService: MutableStateFlow<StateWorkOutDB>){
        if (isBound ) {
            mService?.startWorkout(training, stateService)
        } else { MutableStateFlow(StateWorkOutDB())}
    }
    fun pauseWorkout(){
        if (isBound ) mService?.pauseWorkout()
    }
    fun stopWorkout(){
        mService?.stopWorkout()
        mService?.stopSelf()
    }
    fun <T>bindService( clazz: Class<T>) {
        context.bindService(Intent(context, clazz), serviceConnection, BIND_AUTO_CREATE)
//        log(true, "bindService: $serviceConnection")
    }
    fun unbindService() {
        if (isBound) context.unbindService(serviceConnection)
    }
}
