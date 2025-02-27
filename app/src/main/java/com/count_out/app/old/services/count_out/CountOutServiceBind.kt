//package com.count_out.app.services.count_out
//
//import android.content.ComponentName
//import android.content.Context
//import android.content.ServiceConnection
//import android.os.IBinder
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class CountOutServiceBind @Inject constructor(val context: Context, private val serviceUtils: ServiceUtils
//) {
//    lateinit var service: CountOutService
//
//    private val serviceConnection = object : ServiceConnection {
//        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
//            service = (binder as CountOutService.DistributionServiceBinder).getService()
//        }
//        override fun onServiceDisconnected(arg0: ComponentName) {
//            service.stopCountOutService()
//            service.stopSelf()
//        }
//    }
//    fun bindService(){ bind(CountOutService::class.java) }
//
//    private fun <T>bind(clazz: Class<T>) { serviceUtils.bindService(clazz, serviceConnection) }
//    fun unbindService()  {
//        if (service.running) service.stopCountOutService()
//        serviceUtils.unbindService(serviceConnection, true) }
//}