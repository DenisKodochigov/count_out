package com.count_out.service.service_count_out.models

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import com.count_out.service.service_count_out.entity.ServiceUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceUtilsImpl@Inject constructor(val context: Context): ServiceUtils {

    override fun <T>bindService(clazz: Class<T>, serviceConnection: ServiceConnection) {
        context.bindService(Intent(context, clazz), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun unbindService(serviceConnection: ServiceConnection, isBound: Boolean) {
        if (isBound) context.unbindService(serviceConnection)
    }
}
