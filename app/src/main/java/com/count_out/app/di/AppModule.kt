package com.count_out.app.di

import android.content.Context
//import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.presentation.models.Internet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideInternet(@ApplicationContext appContext: Context): Internet {
        return Internet(appContext)
    }
}