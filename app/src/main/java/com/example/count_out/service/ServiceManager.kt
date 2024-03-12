package com.example.count_out.service

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.StreamsWorkout
import com.example.count_out.entity.Training
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor( val context: Context
){
    private var mService: WorkoutService? = null
    private lateinit var coroutine: CoroutineScope
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
    fun startWorkout(training: Training, streamsWorkout: StreamsWorkout){
        if (isBound ) {
            mService?.startWorkout(training, streamsWorkout)
            coroutine = CoroutineScope(Dispatchers.Default)
            coroutine.launch {
                streamsWorkout.flowMessage = mService?.flowStateService!!
                streamsWorkout.flowTick = mService?.flowTick!!
            }
        } else { MutableStateFlow(StateWorkOut()) }
    }
    fun pauseWorkout(){
        if (isBound ) mService?.pauseWorkout()
    }
    fun stopWorkout(){
        mService?.stopWorkout()
        coroutine.cancel()
    }
    fun <T>bindService( clazz: Class<T>) {
        context.bindService(Intent(context, clazz), serviceConnection, BIND_AUTO_CREATE)
    }
    fun unbindService() {
        if (isBound) context.unbindService(serviceConnection)
    }
}

