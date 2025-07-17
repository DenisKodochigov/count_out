package com.count_out.data.source.room

import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(speechKit: SpeechKitImplD): Flow<ResultSource<SpeechKitImplD>>
    fun copy(speechKit: SpeechKitImplD): Flow<ResultSource<SpeechKitImplD>>
    fun update(speechKit: SpeechKitImplD): Flow<ResultSource<SpeechKitImplD>>
    fun del(speechKit: SpeechKitImplD): Flow<ResultSource<Long>>
}