package com.count_out.device.bluetooth.ai

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BleModule {
    @Provides
    @Singleton
    fun provideHeartRateProvider(@ApplicationContext ctx: Context): HeartRateProvider {
        return BleHeartRateManager(ctx)
    }
}