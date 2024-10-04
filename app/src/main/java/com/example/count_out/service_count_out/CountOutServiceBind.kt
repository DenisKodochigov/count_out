package com.example.count_out.service_count_out

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.RunningState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountOutServiceBind @Inject constructor(
    val context: Context, private val serviceUtils: ServiceUtils
) {
    lateinit var service: CountOutService
    val stateService:MutableStateFlow<RunningState> = MutableStateFlow( RunningState.Stopped)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            service = (binder as CountOutService.DistributionServiceBinder).getService()
            CoroutineScope(Dispatchers.Default).launch {
                service.stateService.collect { state-> stateService.value = state}
            }
            service.stateService.value = RunningState.Binding
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            service.stopSelf()
            stateService.value = RunningState.Stopped
        }
    }
    fun bindService(){ bind(CountOutService::class.java) }

    private fun <T>bind(clazz: Class<T>) { serviceUtils.bindService(clazz, serviceConnection) }
    fun unbindService()  { serviceUtils.unbindService(serviceConnection, true) }
}