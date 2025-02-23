package com.count_out.app.di

//@InstallIn(SingletonComponent::class)
//@Module
//object ApiModule {
//
//    @Singleton
//    @Provides
//    fun provideFactory(): MoshiConverterFactory = MoshiConverterFactory.create()
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(gsonConverterFactory: MoshiConverterFactory): Retrofit {
//        return Retrofit
//            .Builder()
//            .baseUrl("https://api.open-meteo.com/v1/")
//            .addConverterFactory(gsonConverterFactory)
//            .build()
//    }
//    @Singleton
//    @Provides
//    fun provideApi(retrofit: Retrofit): OpenMeteoAPI {
//        return retrofit.create(OpenMeteoAPI::class.java)
//    }
//}