package com.example.data.source.room

import com.example.data.entity.SpeechKitImpl
import com.example.domain.entity.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKit>
    fun add(speechKit: SpeechKitImpl?): Flow<SpeechKit>
    fun update(speechKit: SpeechKitImpl): Flow<SpeechKit>
    fun del(speechKit: SpeechKitImpl)
}