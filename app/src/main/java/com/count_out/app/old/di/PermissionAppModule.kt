//package com.count_out.app.old.di
//
//import android.content.Context
//import com.count_out.app.ui.permission.PermissionApp
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class PermissionAppModule {
//    @Singleton
//    @Provides
//    fun providePermissionApp(@ApplicationContext appContext: Context): PermissionApp {
//        return PermissionApp(appContext)
//    }
//}