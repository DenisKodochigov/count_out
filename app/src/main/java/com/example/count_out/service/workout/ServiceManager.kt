package com.example.count_out.service.workout

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.entity.SendToUI
import com.example.count_out.service.ServiceUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor(val context: Context, val serviceUtils: ServiceUtils
){
    private lateinit var workOutService: WorkoutService
    var isBound : Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            workOutService = (binder as WorkoutService.WorkoutServiceBinder).getService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            workOutService.stopSelf()
            isBound  = false
        }
    }

    fun startWorkout(){ if (isBound ) workOutService.startWorkout() }

    fun connectingToService(variablesIn: SendToWorkService): SendToUI{
        workOutService.variablesIn = variablesIn
        return workOutService.variablesOut
    }

    fun pauseWorkout(){ if (isBound ) workOutService.pauseWorkout() }

    fun stateRunningService() = workOutService.variablesOut.stateRunning.value

    fun stopWorkout(){ workOutService.stopWorkout() }

    fun <T>bindService( clazz: Class<T>) { serviceUtils.bindService(clazz, serviceConnection) }

    fun unbindService()  { serviceUtils.unbindService(serviceConnection, isBound) }

}