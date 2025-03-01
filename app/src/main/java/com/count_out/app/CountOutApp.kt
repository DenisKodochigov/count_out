package com.count_out.app

import android.app.Application
import com.count_out.service.service_count_out.models.CountOutServiceBind
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CountOutApp: Application() {

    @Inject lateinit var serviceBind: CountOutServiceBind

    override fun onCreate() {
        super.onCreate()
        serviceBind.bindService()
    }

    override fun onTerminate() {
        super.onTerminate()
        serviceBind.unbindService()
    }

    companion object App{
        var scale: Int = 1
    }
}