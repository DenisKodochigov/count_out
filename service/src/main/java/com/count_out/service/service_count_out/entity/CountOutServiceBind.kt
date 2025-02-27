package com.count_out.service.service_count_out.entity

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

abstract class CountOutServiceBind{
    abstract fun bindService()
    abstract fun unbindService()
    abstract fun initUtils(): ServiceUtils
    val utils = initUtils()
    lateinit var service1: CountOutService

    fun <T>bind(clazz: Class<T>) { utils.bindService(clazz, serviceConnection) }

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            service1 = (binder as CountOutService.DistributionServiceBinder).getService()
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            service1.stopCountOutService()
            service1.stopSelf()
        }
    }
}