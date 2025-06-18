package com.count_out.data.di

import com.count_out.data.entity.ConverterResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Singleton
    @Provides
    fun provideConverterResult(): ConverterResult = ConverterResult()
}