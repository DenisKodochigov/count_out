package com.count_out.service.service_count_out

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountOutServiceBind @Inject constructor(private val context: Context){
    var isBound: Boolean = false
    lateinit var service: CountOutService

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            service = (binder as CountOutService.DistributionServiceBinder).getService()
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            service.stopCountOutService()
            service.stopSelf()
        }
    }
    private fun <T>bind(clazz: Class<T>) {
        context.bindService(Intent(context, clazz), serviceConnection, Context.BIND_AUTO_CREATE)}
    fun bindService(){ bind(CountOutService::class.java) }
    fun unbindService()  {
        if (service.running) service.stopCountOutService()
        if (isBound) context.unbindService(serviceConnection) }
}
