package com.count_out.data.source.room

import com.count_out.data.models.SpeechKitImpl
import com.count_out.entity.entity.workout.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKit>
    fun add(): Flow<SpeechKit>
    fun addL(): Long
    fun copy(speechKit: SpeechKitImpl): Flow<SpeechKit>
    fun copyL(speechKit: SpeechKitImpl): Long
    fun update(speechKit: SpeechKitImpl): Flow<SpeechKit>
    fun del(speechKit: SpeechKitImpl)
}