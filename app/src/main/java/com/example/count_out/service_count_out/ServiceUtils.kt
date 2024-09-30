package com.example.count_out.service_count_out

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceUtils@Inject constructor(val context: Context) {

    fun <T>bindService(clazz: Class<T>, serviceConnection: ServiceConnection) {
        context.bindService(Intent(context, clazz), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(serviceConnection: ServiceConnection, isBound: Boolean) {
        if (isBound) context.unbindService(serviceConnection)
    }
}