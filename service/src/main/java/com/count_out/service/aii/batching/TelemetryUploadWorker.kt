package com.count_out.service.aii.batching

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.count_out.framework.room.db.telemetriy.TelemetryDao
import kotlinx.coroutines.delay

class TelemetryUploadWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val telemetryDao: TelemetryDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // загружаем пачками пока есть pending
            while (true) {
                val pending = telemetryDao.loadPending(limit = 100)
                if (pending.isEmpty()) break

                // Сформировать payload
                val payload = pending.map { it.toDto() } // расширение для преобразования в DTO

                // Тут -- сетевой вызов (Retrofit/OkHttp). Пример: ApiService.uploadTelemetry(payload)
                val success = uploadToServer(payload)

                if (success) {
                    telemetryDao.markSynced(pending.map { it.id })
                } else {
                    // если сетевой сбой — остановиться и вернуть retry
                    return Result.retry()
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun uploadToServer(payload: List<Map<String, Any>>): Boolean {
        // Здесь — реализация сетевого запроса. Для примера — эмуляция:
        delay(300)
        return true
    }
}