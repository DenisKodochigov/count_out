package com.example.count_out.service

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor( val context: Context
){
    private var mService: WorkoutService? = null
    var isBound : Boolean = false
    var stop = false
    var isStarted = false
    lateinit var flowTick: MutableStateFlow<TickTime>
    lateinit var flowStateService: MutableStateFlow<StateWorkOut>

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
    fun startWorkout(training: Training){
        if (isBound ) {
            flowTick = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00"))
            flowStateService = MutableStateFlow(StateWorkOut())
            mService?.startWorkout(training)
            flowStateService = mService?.flowStateService!!
            flowTick = mService?.flowTick!!
            isStarted = true
        }
    }
    fun pauseWorkout(){
        if (isBound ) mService?.pauseWorkout()
    }
    fun stopWorkout(){
        stop = true
        isStarted = true
        mService?.stopWorkout()
    }
    fun <T>bindService( clazz: Class<T>) {
        context.bindService( Intent(context, clazz), serviceConnection, BIND_AUTO_CREATE)
    }
    fun unbindService() {
        if (isBound) context.unbindService(serviceConnection)
    }
}

