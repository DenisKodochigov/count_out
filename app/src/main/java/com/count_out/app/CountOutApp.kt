package com.count_out.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CountOutApp: Application() {

    companion object App{
        var scale: Int = 1
    }
}