//package com.count_out.app.old.di
//
//import android.content.Context
//import com.count_out.app.ui.notification.NotificationHelper
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class NotificationModule {
//    @Singleton
//    @Provides
//    fun provideNotificationApp(@ApplicationContext appContext: Context): NotificationHelper {
//        return NotificationHelper( appContext )
//    }
//}