package com.count_out.framework.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.local.LastBleDeviceSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LastBleDeviceSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): LastBleDeviceSource, PrimeSource() {
    internal val keyName = stringPreferencesKey("last_device")

    override suspend fun saveDevice(addr: TypeSource): Flow<ResultSource<TypeSource>> {
        return flow { emit(
            try {
                if (addr is TypeSource.StringT) {
                    dataStore.edit { it[keyName] = addr.item }
                    ResultSource.Success(TypeSource.BooleanT(true))
                } else ResultSource.Error(ThrowableDS.NotValidType())
            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e))}
        )}

//        val result: MutableStateFlow<ResultSource<TypeSource>> =
//            MutableStateFlow(ResultSource.Success(TypeSource.BooleanT(true)))
//        val scope = CoroutineScope(Dispatchers.IO)
//        scope.launch {
//            result.value = try {
//                if (addr is TypeSource.StringT) {
//                    dataStore.edit { it[keyName] = addr.item }
//                    ResultSource.Success(TypeSource.BooleanT(true))
//                } else ResultSource.Error(ThrowableDS.NotValidType())
//            }catch (e: Exception) {ResultSource.Error(ThrowableDS.extract(e))}
//            finally { scope.cancel() }
//        }
////            .invokeOnCompletion{t->
////            if(t != null) ResultSource.Error(ThrowableDS.extract(t))
////            scope.cancel()
////        }
//        return flow { emit(result.value)}
    }

    override fun getDevice(): Flow<ResultSource<TypeSource>> {
        return dataStore.data.map {TypeSource.StringT(it[keyName] ?: "") }.resultSource()
    }
}
