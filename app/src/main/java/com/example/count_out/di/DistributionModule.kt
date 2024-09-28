package com.example.count_out.di

import android.content.Context
import com.example.count_out.service.ServiceUtils
import com.example.count_out.service_app.DistributionService
import com.example.count_out.service_app.DistributionServiceBind
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DistributionModule {

    @Singleton
    @Provides
    fun provideDistributionService(@ApplicationContext appContext: Context): DistributionService {
        return DistributionService()
    }
    @Singleton
    @Provides
    fun provideDistributionServiceBind(@ApplicationContext appContext: Context,
                                       serviceUtils: ServiceUtils): DistributionServiceBind {
        return DistributionServiceBind(appContext, serviceUtils)
    }
}