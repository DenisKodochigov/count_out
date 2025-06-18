package com.count_out.data.source.room

import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultDataSource
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(speechKit: SpeechKitImplD): Flow<ResultDataSource<SpeechKitImplD>>
    fun copy(speechKit: SpeechKitImplD): Flow<ResultDataSource<SpeechKitImplD>>
    fun update(speechKit: SpeechKitImplD): Flow<ResultDataSource<SpeechKitImplD>>
    fun del(speechKit: SpeechKitImplD): Flow<ResultDataSource<Long>>
}