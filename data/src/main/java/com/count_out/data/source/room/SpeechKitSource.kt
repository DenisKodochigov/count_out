package com.count_out.data.source.room

import com.count_out.domain.entity.workout.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKit>
    fun copy(speechKit: SpeechKit): Long
    fun update(speechKit: SpeechKit)
    fun del(speechKit: SpeechKit)
}