package com.example.data.source.room

import com.example.data.entity.SpeechImpl
import com.example.domain.entity.Speech
import kotlinx.coroutines.flow.Flow

interface SpeechSource {
    fun get(id: Long): Flow<Speech>
    fun add(speech: SpeechImpl): Flow<Speech>
    fun update(speech: SpeechImpl): Flow<Speech>
    fun updateDuration(speech: SpeechImpl): Flow<Speech>
    fun del(id: Long)
}