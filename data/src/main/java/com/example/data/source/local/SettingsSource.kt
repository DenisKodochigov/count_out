package com.example.data.source.local

import kotlinx.coroutines.flow.Flow

interface SettingsSource {
    fun getSettingSpeechDescr(): Flow<Boolean>
    suspend fun saveSettingSpeechDescr(settings: Boolean)

}