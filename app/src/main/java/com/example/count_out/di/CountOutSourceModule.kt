package com.example.count_out.di

import com.example.count_out.data.source.services.CountOutServiceSource
import com.example.count_out.services.count_out.CountOutServiceSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CountOutSourceModule {
    @Binds
    abstract fun bindCountOutSource(trainingSource: CountOutServiceSourceImpl): CountOutServiceSource

}