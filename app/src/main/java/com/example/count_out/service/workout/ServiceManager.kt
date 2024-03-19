package com.example.count_out.service.workout

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor(val context: Context
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
    fun startWorkout(variablesIn: VariablesInService): VariablesOutService{
        val variablesOut = connectingToService(variablesIn)
        if (isBound ) {
            workOutService.startWorkout()
        }
        return variablesOut
    }

    fun connectingToService(variablesIn: VariablesInService): VariablesOutService{
        workOutService.variablesIn = variablesIn
        return workOutService.variablesOut
    }

    fun pauseWorkout(){
        if (isBound ) {
            workOutService.pauseWorkout()
        }
    }
    fun stateRunningService() = workOutService.variablesOut.stateRunning.value
    fun stopWorkout(){
        workOutService.stopWorkout()
    }
    fun <T>bindService( clazz: Class<T>) {
        context.bindService( Intent(context, clazz), serviceConnection, Context.BIND_AUTO_CREATE)
    }
    fun unbindService() {
        if (isBound) context.unbindService(serviceConnection)
    }
}


//@Singleton
//class ServiceManager @Inject constructor( val context: Context
//){
//    private var mService: WorkoutService? = null
//    private val listCondition: MutableList<VariablesOutService> = mutableListOf()
//    private lateinit var coroutine: CoroutineScope
//
//    lateinit var flowTick: MutableStateFlow<TickTime>
//    lateinit var flowListOut: MutableStateFlow<List<VariablesOutService>>
//    var flowTraining = MutableStateFlow(TrainingDB() as Training)
//    var isBound : Boolean = false
//    var isStarted: StateRunning = StateRunning.Stoped
////=======================================================================
//    var flowIn: VariablesInService = VariablesInService()
////=======================================================================
//    private val serviceConnection = object : ServiceConnection {
//        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
//            val binderService = binder as WorkoutService.WorkoutServiceBinder
//            mService = binderService.getService()
//            isBound  = true
//        }
//        override fun onServiceDisconnected(arg0: ComponentName) {
//            mService = null
//            isBound  = false
//        }
//    }
//    suspend fun startWorkout(){
//        if (isBound ) {
////=======================================================================
//            mService?.flowIn
//            flowTick = MutableStateFlow(TickTime(hour = "00", min="00", sec= "00"))
//            flowListOut = MutableStateFlow(emptyList())
//            coroutine = CoroutineScope(Dispatchers.Default)
//            mService?.training = flowTraining
//            mService?.startWorkout()
//            isStarted = StateRunning.Started
//            flowTick = mService?.flowTick!!
//            coroutine.launch {
//                mService?.flowOut!!.collect{ condition ->
//                    listCondition.add(condition)
//                    flowListOut.value = listCondition
//                    condition.stateRunning?.let { if (it == StateRunning.Stoped) stopWorkout() }
//                }
//            }
//        }
//    }
//
//    fun pauseWorkout(){
//        if (isBound ) {
//            mService?.pauseWorkout()
//            isStarted = StateRunning.Pause
//        }
//    }
//    fun continuationService(){
//        if (isBound ) {
//            mService?.pauseWorkout()
//            isStarted = StateRunning.Started
//        }
//    }
//    fun stopWorkout(){
//        isStarted = StateRunning.Stoped
//        listCondition.clear()
//        coroutine.cancel()
//        mService?.stopWorkout()
//    }
//    fun <T>bindService( clazz: Class<T>) {
//        context.bindService( Intent(context, clazz), serviceConnection, BIND_AUTO_CREATE)
//    }
//    fun unbindService() {
//        if (isBound) context.unbindService(serviceConnection)
//    }
//}
//
