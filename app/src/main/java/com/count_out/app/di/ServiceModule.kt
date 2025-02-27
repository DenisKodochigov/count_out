package com.count_out.app.di

import com.count_out.service.service_count_out.entity.CountOutServiceBind
import com.count_out.service.service_count_out.models.CountOutServiceBindImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun bindServiceCount(service: CountOutServiceBindImpl): CountOutServiceBind
}