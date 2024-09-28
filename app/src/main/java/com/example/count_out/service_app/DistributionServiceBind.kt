package com.example.count_out.service_app

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.service.ServiceUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DistributionServiceBind @Inject constructor(
    val context: Context, val serviceUtils: ServiceUtils) {

    lateinit var service: DistributionService
    val isBound : MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            service = (binder as DistributionService.DistributionServiceBinder).getService()
            isBound.value  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            service.stopSelf()
            isBound.value  = false
        }
    }
    suspend fun bindService(){
        bind(DistributionService::class.java)
        while (!isBound.value) { delay(100L) }
    }
    fun <T>bind(clazz: Class<T>) { serviceUtils.bindService(clazz, serviceConnection) }
    fun unbindService()  { serviceUtils.unbindService(serviceConnection, isBound.value) }
}