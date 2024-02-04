package com.example.count_out

import android.app.Application
import com.example.count_out.ui.joint.NotificationApp
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppBase: Application() {
    @Inject lateinit var notificationApp: NotificationApp
    override fun onCreate() {
        super.onCreate()
        notificationApp.createNotificationChannel()
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
    companion object App{
        var scale: Int = 1
    }
}