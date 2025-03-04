package com.count_out.app

import android.app.Application
import com.count_out.domain.use_case.other.CountOutServiceBindUC
import com.count_out.domain.use_case.other.CountOutServiceUnBindUC
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CountOutApp: Application() {

//    @Inject lateinit var serviceBind: CountOutServiceBind
    @Inject lateinit var countOutServiceBind: CountOutServiceBindUC
    @Inject lateinit var countOutServiceUnBind: CountOutServiceUnBindUC

    override fun onCreate() {
        super.onCreate()
        countOutServiceBind.execute(CountOutServiceBindUC.Request)
//        serviceBind.bindService()
    }

    override fun onTerminate() {
        super.onTerminate()
        countOutServiceUnBind.execute(CountOutServiceUnBindUC.Request)
//        serviceBind.unbindService()
    }

    companion object App{
        var scale: Int = 1
    }
}