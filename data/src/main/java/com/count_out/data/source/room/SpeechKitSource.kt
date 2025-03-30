package com.count_out.data.source.room

import com.count_out.data.models.SpeechKitImplD
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(id: Long): Flow<SpeechKitImplD?>
    fun copy(speechKit: SpeechKitImplD): Long
    fun update(speechKit: SpeechKitImplD)
    fun del(speechKit: SpeechKitImplD)
}