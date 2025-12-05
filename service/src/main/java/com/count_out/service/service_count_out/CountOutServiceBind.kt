package com.count_out.service.service_count_out

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.types_domai.BooleanDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout_service.BindServiceCountOut
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountOutServiceBind @Inject constructor(private val context: Context): BindServiceCountOut {
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
    override fun bindService(): Flow<ResultDomain<Domain>>{
        return flowOf( runCatching { bind(CountOutService::class.java)
            ResultDomain.Success(BooleanDm(true))}
            .getOrElse { ResultDomain.Error(ThrowableUC.ErrorBindingService()) })

    }
    override fun unbindService(): Flow<ResultDomain<Domain>> {
        return flowOf(runCatching {
//        if (service.running) service.stopCountOutService()
            if (isBound) context.unbindService(serviceConnection)
            ResultDomain.Success(BooleanDm(true))
        }.getOrElse { ResultDomain.Error(ThrowableUC.ErrorUnBindingService()) })
    }
}
