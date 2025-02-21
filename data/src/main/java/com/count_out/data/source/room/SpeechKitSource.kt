package com.count_out.data.source.room

import com.count_out.data.entity.SpeechKitImpl
import com.count_out.domain.entity.SpeechKit
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKit>
    fun add(): Flow<SpeechKit>
    fun copy(speechKit: SpeechKitImpl): Flow<SpeechKit>
    fun update(speechKit: SpeechKitImpl): Flow<SpeechKit>
    fun del(speechKit: SpeechKitImpl)
}