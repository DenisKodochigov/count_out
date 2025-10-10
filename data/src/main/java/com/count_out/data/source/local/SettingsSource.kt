package com.count_out.data.source.local

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import kotlinx.coroutines.flow.Flow

interface SettingsSource {
    fun getSettings(): Flow<ResultData<Data>>
    fun getSettingSpeechDescr(): Flow<ResultData<Data>>
    fun getBleAddress(): Flow<ResultData<Data>>
    fun getBleName(): Flow<ResultData<Data>>
    fun saveBleName(settings: Data): Flow<ResultData<Data>>
    fun saveBleAddress(settings: Data): Flow<ResultData<Data>>
    fun saveSettingSpeechDescr(settings: Data): Flow<ResultData<Data>>
}