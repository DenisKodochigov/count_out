package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.local.LastPlanSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LastPlanSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LastPlanSource, PrimeSource() {
    internal val keyName = longPreferencesKey("last_plan")
    override fun getLastPlan(): Flow<ResultSource<TypeSource>> =
        dataStore.data.map {
            ResultSource.Success(TypeSource.LongT(it[keyName] ?: 1) )}

    override fun saveLastPlan(id: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(try {
                if (id is TypeSource.LongT) {
                    dataStore.edit { it[keyName] = id.item }
                    ResultSource.Success(TypeSource.BooleanT(true))
                } else ResultSource.Error(ThrowableDS.NotValidType())
            } catch (e: Exception){ ResultSource.Error(ThrowableDS.extract(t = e))}
        )}
    }
}
