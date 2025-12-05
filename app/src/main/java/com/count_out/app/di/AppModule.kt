package com.count_out.app.di

import android.content.Context
import com.count_out.framework.room.db.telemetriy.TelemetryDao
//import com.count_out.domain.use_case.plans.GetPlanUC
import com.count_out.presentation.models.Internet
import com.count_out.service.aii.batching.TelemetryBatchWriterChannel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideInternet(@ApplicationContext appContext: Context): Internet {
        return Internet(appContext)
    }

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // Provide BatchWriter singleton
    @Provides
    @Singleton
    fun provideTelemetryBatchWriter(dao: TelemetryDao, @ApplicationScope scope: CoroutineScope): TelemetryBatchWriter {
        return TelemetryBatchWriter(dao, scope)
    }

    @Provides
    @Singleton
    fun provideTelemetryBatchWriterChannel(dao: TelemetryDao, @ApplicationScope scope: CoroutineScope): TelemetryBatchWriterChannel {
        return TelemetryBatchWriterChannel(dao, scope)
    }
}
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope