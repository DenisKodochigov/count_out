package com.count_out.framework.room.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.count_out.data.source.services.TelemetrySource
import com.count_out.domain.entity.ai.TelemetryPoint
import com.count_out.framework.room.db.telemetriy.TelemetryDao
import com.count_out.framework.room.db.telemetriy.TelemetryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TelemetrySourceImpl @Inject constructor(
    private val dao: TelemetryDao,
    private val dataStore: DataStore<Preferences>): TelemetrySource, PrimeSource()
{
    internal val key = booleanPreferencesKey("use_simulator")

    override fun insertBatch(points: List<TelemetryPoint>) {
        dao.upsertAll(points.map {
            TelemetryEntity(
                timestamp = it.timestamp,
                lat = it.lat,
                lon = it.lon,
                heartRate = it.heartRate
            )
        })
    }

    override fun getPendingCountFlow(): Flow<Int> = dao.countPendingFlow()

    override fun loadPending(limit: Int) =
        dao.loadPending(limit).map {
            TelemetryPoint(
                timestamp = it.timestamp,
                lat = it.lat,
                lon = it.lon,
                heartRate = it.heartRate
            )
        }
    override fun markSynced(ids: List<Long>) = dao.markSynced(ids)

    // Используем Room Flow для прямого обновления количества pending записей
    val pendingCountFlow: Flow<Int> = dao.countPendingFlow()

    override fun getPendingCount(): Int {
        return dao.countPending() }

//    override fun triggerImmediateUpload() {
//        TODO("Not yet implemented")
//    }
//    override fun setUseSimulator(enabled: Boolean) {
//        dataStore.edit { prefs -> prefs[key] = enabled }
//    }
    override fun isUsingSimulatorFlow(): Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey("use_simulator")] ?: false }
}