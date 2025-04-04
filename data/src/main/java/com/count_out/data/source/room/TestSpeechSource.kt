package com.count_out.data.source.room

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.throwable.ResultDataSource
import kotlinx.coroutines.flow.Flow

interface TestSpeechSource {
    fun get(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>>
    fun copy(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>>
    fun update(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>>
}