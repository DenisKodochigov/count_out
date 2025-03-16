package com.count_out.data.source.local

import com.count_out.domain.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsSource {
    fun getSettings(): Flow<Settings>
    fun getSettingSpeechDescr(): Flow<Boolean>
    fun getBleAddress(): Flow<String>
    fun getBleName(): Flow<String>
    fun saveBleName(settings: String)
    fun saveBleAddress(settings: String)
    fun saveSettingSpeechDescr(settings: Boolean)
}