package com.example.count_out

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppBase: Application() {
    override fun onCreate() {
        super.onCreate()

//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
    companion object App{
        var scale: Int = 1
    }
}