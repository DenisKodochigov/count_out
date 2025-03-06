package com.count_out.entity.di

import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
class DependenciesKeyModule {
    @MapKey
    annotation class DependenciesKey(val value: KClass<out Dependencies>)
}
