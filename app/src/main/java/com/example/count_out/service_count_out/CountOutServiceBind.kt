package com.example.count_out.service_count_out

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountOutServiceBind @Inject constructor(
    val context: Context, private val serviceUtils: ServiceUtils
) {

    lateinit var service: CountOutService
    val isBound : MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            service = (binder as CountOutService.DistributionServiceBinder).getService()
            isBound.value  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            service.stopSelf()
            isBound.value  = false
        }
    }
    suspend fun bindService(){
        bind(CountOutService::class.java)
        while (!isBound.value) { delay(100L) }
    }
    private fun <T>bind(clazz: Class<T>) { serviceUtils.bindService(clazz, serviceConnection) }
    fun unbindService()  { serviceUtils.unbindService(serviceConnection, isBound.value) }
}