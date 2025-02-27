package com.count_out.service.service_count_out.models

import android.content.Context
import com.count_out.service.service_count_out.entity.CountOutServiceBind
import com.count_out.service.service_count_out.entity.ServiceUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountOutServiceBindImpl @Inject constructor(
    val context: Context, private val serviceUtils: ServiceUtilsImpl
): CountOutServiceBind(){
    lateinit var service: CountOutServiceImpl

    override fun initUtils(): ServiceUtils = serviceUtils

    override fun bindService(){ bind(CountOutServiceImpl::class.java) }

    override fun unbindService()  {
        if (service.running) service.stopCountOutService()
        serviceUtils.unbindService(serviceConnection, true) }
}