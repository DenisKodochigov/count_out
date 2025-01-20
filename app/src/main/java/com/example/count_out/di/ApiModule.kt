package com.example.count_out.di

import com.example.count_out.data.openmeteo_api.OpenMeteoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: MoshiConverterFactory): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): OpenMeteoAPI {
        return retrofit.create(OpenMeteoAPI::class.java)
    }
}