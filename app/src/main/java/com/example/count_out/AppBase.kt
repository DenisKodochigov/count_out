package com.example.count_out

import android.app.Application
import android.content.ServiceConnection
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppBase: Application() {
//    @Inject lateinit var notificationApp: NotificationApp
    override fun onCreate() {
        super.onCreate()
//        notificationApp.createNotificationChannel()
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
    companion object App{
        var scale: Int = 1
        val unbindService:(ServiceConnection)->Unit ={  }
        val bindService:(ServiceConnection)->Unit ={}
    }
}