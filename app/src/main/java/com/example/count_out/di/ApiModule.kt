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
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val serverApi = "https://api.open-meteo.com/v1/"
        return Retrofit
            .Builder()
            .baseUrl(serverApi)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    @Provides
    fun provideApi(retrofit: Retrofit): OpenMeteoAPI {
        return retrofit.create(OpenMeteoAPI::class.java)
    }
}