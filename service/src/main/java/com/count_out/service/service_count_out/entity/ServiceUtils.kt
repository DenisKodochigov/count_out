package com.count_out.service.service_count_out.entity

import android.content.ServiceConnection

interface ServiceUtils {
    fun <T>bindService(clazz: Class<T>, serviceConnection: ServiceConnection)
    fun unbindService(serviceConnection: ServiceConnection, isBound: Boolean)
}