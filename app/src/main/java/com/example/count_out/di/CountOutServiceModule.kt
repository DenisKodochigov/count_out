package com.example.count_out.di

import android.content.Context
import com.example.count_out.devices.location.LocationWithOutGoogle
import com.example.count_out.devices.location.Site
import com.example.count_out.framework.speech.SpeechManager
import com.example.count_out.entity.permission.PermissionApp
import com.example.count_out.services.count_out.CountOutService
import com.example.count_out.services.count_out.CountOutServiceBind
import com.example.count_out.services.count_out.ServiceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CountOutServiceModule {

    @Singleton
    @Provides
    fun provideDistributionService(): CountOutService {
        return CountOutService()
    }
    @Singleton
    @Provides
    fun provideDistributionServiceBind(@ApplicationContext appContext: Context,
                                       serviceUtils: ServiceUtils
    ): CountOutServiceBind {
        return CountOutServiceBind(appContext)
    }


    @Singleton
    @Provides
    fun provideSite(@ApplicationContext appContext: Context, permissionApp: PermissionApp,): Site {
        return Site(appContext, permissionApp)
    }

//    @Singleton
//    @Provides
//    fun provideLogging(dataRepository: DataRepository): Logging {
//        return Logging(dataRepository)
//    }

    @Singleton
    @Provides
    fun provideLocationWithOutGoogle(@ApplicationContext appContext: Context, permissionApp: PermissionApp,
    ): LocationWithOutGoogle {
        return LocationWithOutGoogle(appContext, permissionApp)
    }

    @Singleton
    @Provides
    fun provideServiceUtils(@ApplicationContext context: Context) = ServiceUtils(context)

    @Singleton
    @Provides
    fun provideSpeechManager(@ApplicationContext appContext: Context): SpeechManager =
        SpeechManager(appContext)
}