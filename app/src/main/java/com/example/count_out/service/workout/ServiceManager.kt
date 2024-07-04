package com.example.count_out.service.workout

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
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
            val binderService = binder as WorkoutService.WorkoutServiceBinder
            workOutService = binderService.getService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            workOutService.stopSelf()
            isBound  = false
        }
    }

    fun startWorkout(){ if (isBound ) workOutService.startWorkout() }

    fun connectingToService(variablesIn: VariablesInService): VariablesOutService{
        workOutService.variablesIn = variablesIn
        return workOutService.variablesOut
    }

    fun pauseWorkout(){ if (isBound ) workOutService.pauseWorkout() }

    fun stateRunningService() = workOutService.variablesOut.stateRunning.value

    fun stopWorkout(){ workOutService.stopWorkout() }

    fun <T>bindService( clazz: Class<T>) { serviceUtils.bindService(clazz, serviceConnection) }

    fun unbindService()  { serviceUtils.unbindService(serviceConnection, isBound) }

}