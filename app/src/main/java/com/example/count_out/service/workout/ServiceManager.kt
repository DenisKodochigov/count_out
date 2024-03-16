package com.example.count_out.service.workout

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TickTime
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
    private val listStateService: MutableList<StateWorkOut> = mutableListOf()
    private lateinit var coroutine: CoroutineScope

    lateinit var flowTick: MutableStateFlow<TickTime>
    lateinit var flowStateService: MutableStateFlow<List<StateWorkOut>>
    var flowTraining = MutableStateFlow(TrainingDB() as Training)
    var isBound : Boolean = false
    var isStarted = false

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
    suspend fun startWorkout(){
        if (isBound ) {
            flowTick = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00"))
            flowStateService = MutableStateFlow(emptyList())
            coroutine = CoroutineScope(Dispatchers.Default)
            mService?.training = flowTraining
            mService?.startWorkout()
            isStarted = true
            flowTick = mService?.flowTick!!

            coroutine.launch {
                mService?.flowStateService!!.collect{ state ->
                    listStateService.add(state)
                    flowStateService.value = listStateService
                }
            }
        }
    }

    fun pauseWorkout(){
        if (isBound ) mService?.pauseWorkout()
    }
    fun stopWorkout(){
        isStarted = false
        listStateService.clear()
        coroutine.cancel()
        mService?.stopWorkout()
    }
    fun <T>bindService( clazz: Class<T>) {
        context.bindService( Intent(context, clazz), serviceConnection, BIND_AUTO_CREATE)
    }
    fun unbindService() {
        if (isBound) context.unbindService(serviceConnection)
    }
}

