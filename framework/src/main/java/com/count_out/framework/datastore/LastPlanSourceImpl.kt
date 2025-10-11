package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.BooleanDb
import com.count_out.data.models.types_data.LongDb
import com.count_out.framework.room.source.PrimeSource
import com.count_out.data.source.local.LastPlanSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LastPlanSourceImpl @Inject constructor( private val dataStore: DataStore<Preferences>
): LastPlanSource, PrimeSource() {
    internal val keyName = longPreferencesKey("last_plan")
    override fun getLastPlan(): Flow<ResultData<Data>> =
        dataStore.data.map { ResultData.Success(LongDb(it[keyName] ?: 1))}

    override fun saveLastPlan(id: Data): Flow<ResultData<Data>> {
        return flow { emit(try {
                if (id is LongDb) {
                    dataStore.edit { it[keyName] = id.item }
                    ResultData.Success(BooleanDb(true))
                } else ResultData.Error(ThrowableDS.NotValidType())
            } catch (e: Exception){ ResultData.Error(ThrowableDS.extract(t = e))}
        )}
    }
}
