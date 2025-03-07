package com.example.count_out.data.source.room

import com.example.count_out.entity.workout.speech.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKit>
    fun add(speechKit: SpeechKit?): Long
    fun copy(speechKit: SpeechKit?): Long
    fun update(speechKit: SpeechKit): Flow<SpeechKit>
    fun del(speechKit: SpeechKit)
}