package com.count_out.data.source.local

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface SettingsSource {
    fun getSettings(): Flow<ResultSource<TypeSource>>
    fun getSettingSpeechDescr(): Flow<ResultSource<TypeSource>>
    fun getBleAddress(): Flow<ResultSource<TypeSource>>
    fun getBleName(): Flow<ResultSource<TypeSource>>
    suspend fun saveBleName(settings: TypeSource): Flow<ResultSource<TypeSource>>
    suspend fun saveBleAddress(settings: TypeSource): Flow<ResultSource<TypeSource>>
    suspend fun saveSettingSpeechDescr(settings: TypeSource): Flow<ResultSource<TypeSource>>
}