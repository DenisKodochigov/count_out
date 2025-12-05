package com.count_out.service.aii.batching

import com.count_out.app.di.ApplicationScope
import com.count_out.framework.room.db.telemetriy.TelemetryDao
import com.count_out.framework.room.db.telemetriy.TelemetryEntity
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class TelemetryBatchWriterChannel @Inject constructor(
    private val telemetryDao: TelemetryDao,
    @ApplicationScope private val appScope: CoroutineScope
) {
    private val channel = Channel<TelemetryEntity>(capacity = Channel.UNLIMITED)
    private val job = appScope.launch(Dispatchers.IO) {
        val buffer = ArrayList<TelemetryEntity>()
        for (item in channel) {
            buffer.add(item)
            if (buffer.size >= 20) {
                telemetryDao.upsertBatch(buffer.toList())
                buffer.clear()
            }
        }
        // канал закрыт — сбросить остаток
        if (buffer.isNotEmpty()) {
            telemetryDao.upsertBatch(buffer.toList())
            buffer.clear()
        }
    }

    suspend fun add(entry: TelemetryEntity) = channel.send(entry)
    suspend fun closeAndFlush() {
        channel.close()
        job.join()
    }
}