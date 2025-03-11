package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.workout.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechRepo {
    fun get(id: Long): Flow<SpeechKit>
    fun del(speechKit: SpeechKit)
    fun add(speechKit: SpeechKit?): Flow<SpeechKit>
    fun update(speechKit: SpeechKit): Flow<SpeechKit>
}