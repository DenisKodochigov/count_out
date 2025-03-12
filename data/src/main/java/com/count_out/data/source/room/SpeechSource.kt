package com.count_out.data.source.room

import com.count_out.data.models.SpeechImpl
import com.count_out.domain.entity.workout.Speech
import kotlinx.coroutines.flow.Flow

interface SpeechSource {
    fun get(id: Long): Flow<Speech>
    fun copy(speech: SpeechImpl): Long
    fun update(speech: SpeechImpl)
//    fun updateDuration(speech: SpeechImpl): Flow<Speech>
    fun del(id: Long)
}