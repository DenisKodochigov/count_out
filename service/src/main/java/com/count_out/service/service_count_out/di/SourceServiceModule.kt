package com.count_out.service.service_count_out.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceServiceModule {
//    @Binds
//    abstract fun bindCountOutServiceSource(source: CountOutServiceSourceImpl): CountOutServiceSource
}