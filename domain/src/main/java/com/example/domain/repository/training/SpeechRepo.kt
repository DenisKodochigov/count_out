package com.example.domain.repository.training

import com.example.domain.entity.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechRepo {
//    fun get(id: Long): Flow<SpeechKit>
//    fun del(speechKit: SpeechKit)
//    fun add(speechKit: SpeechKit?): Flow<SpeechKit>
    fun update(speechKit: SpeechKit): Flow<SpeechKit>
}