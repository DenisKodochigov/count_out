package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.source.SourceData
import com.count_out.data.source.local.LastPlanSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class LastPlanSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>): LastPlanSource, SourceData() {
    internal val keyName = longPreferencesKey("last_plan")

    override fun getLastPlan(): Flow<ResultDataSource<Long>> {
        return getResultFlow { dataStore.data.map { it[keyName] ?: 1 }
    }
    }
    override fun saveLastPlan(id: Long): Flow<ResultDataSource<Boolean>> {
        CoroutineScope(Dispatchers.IO).launch { dataStore.edit { it[keyName] = id } }
        return flow { emit(ResultDataSource.Success(true)) }
    }
}
