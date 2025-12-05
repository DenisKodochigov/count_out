package com.count_out.service.aii.batching

//import android.content.Context
//import androidx.work.ListenableWorker
//import androidx.work.WorkManager
//import androidx.work.WorkerParameters
//import com.count_out.framework.room.db.telemetriy.TelemetryDao
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object WorkModule {
//
//    @Provides
//    @Singleton
//    fun provideWorkManager(@ApplicationContext ctx: Context): WorkManager = WorkManager.getInstance(ctx)
//
//    @Provides
//    fun provideTelemetryUploadWorkerFactory(telemetryDao: TelemetryDao): CoroutineWorkerFactory {
//        // Если вы используете AssistedInject/WorkerFactory — внедрите Dao в фабрику.
//        // Ниже — примерная заглушка; в production лучше реализовать WorkerFactory с Hilt.
//        return object : CoroutineWorkerFactory {
//            override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
//                return TelemetryUploadWorker(appContext, params, telemetryDao)
//            }
//        }
//    }
//}