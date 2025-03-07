package com.example.count_out.data.source.room

import com.example.count_out.entity.workout.speech.Speech
import kotlinx.coroutines.flow.Flow

interface SpeechSource {
    fun get(id: Long): Flow<Speech>
    fun add(speech: Speech?): Long
    fun copy(speech: Speech?): Long
    fun update(speech: Speech): Flow<Speech>
    fun updateDuration(speech: Speech): Flow<Speech>
    fun del(id: Long)
}