package com.count_out.framework.room.db.telemetriy

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TelemetryDao {
    @Upsert
    fun upsertAll(list: List<TelemetryEntity>)
    @Transaction
    fun upsertBatch(list: List<TelemetryEntity>) { upsertAll(list) }
    @Query("SELECT * FROM telemetry_tb WHERE synced = 0 ORDER BY timestamp ASC LIMIT :limit")
    fun loadPending(limit: Int): List<TelemetryEntity>

    @Query("UPDATE telemetry_tb SET synced = 1 WHERE id IN (:ids)")
    fun markSynced(ids: List<Long>)

    // Новый метод: потоковое обновление количества pending записей
    @Query("SELECT COUNT(*) FROM telemetry_tb WHERE synced = 0")
    fun countPendingFlow(): Flow<Int>

    // Для синхронного получения количества pending записей
    @Query("SELECT COUNT(*) FROM telemetry_tb WHERE synced = 0")
    fun countPending(): Int
    @Query("SELECT * FROM telemetry_tb ORDER BY id LIMIT :limit")
    suspend fun getBatch(limit: Int): List<TelemetryEntity>
    @Query("DELETE FROM telemetry_tb WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>)
}