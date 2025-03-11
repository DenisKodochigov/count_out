package com.count_out.data.source.room

import com.count_out.domain.entity.workout.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKit>
    fun add(): Flow<SpeechKit>
    fun addL(): Long
    fun copy(speechKit: SpeechKit): Flow<SpeechKit>
    fun copyL(speechKit: SpeechKit): Long
    fun update(speechKit: SpeechKit): Flow<SpeechKit>
    fun del(speechKit: SpeechKit)
}