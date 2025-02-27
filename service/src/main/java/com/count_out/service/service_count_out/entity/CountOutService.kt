package com.count_out.service.service_count_out.entity

import android.os.Binder

abstract class CountOutService {

    inner class DistributionServiceBinder: Binder() { fun getService(): CountOutService = this@CountOutService }

    abstract fun stopCountOutService()
    abstract fun stopSelf()
}