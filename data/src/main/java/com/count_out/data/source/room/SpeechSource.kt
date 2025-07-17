package com.count_out.data.source.room

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.throwable.ResultSource
import kotlinx.coroutines.flow.Flow

interface SpeechSource {
//    fun get(id: Long): Flow<SpeechImplD?>
//    fun copy(speech: SpeechImplD): Long?
//    fun update(speech: SpeechImplD)
////    fun updateDuration(speech: SpeechImpl): Flow<Speech>
//    fun del(id: Long)

    fun get(speech: SpeechImplD): Flow<ResultSource<SpeechImplD>>
    fun copy(speech: SpeechImplD): Flow<ResultSource<SpeechImplD>>
    fun del(speech: SpeechImplD): Flow<ResultSource<Int>>
    fun update(speech: SpeechImplD): Flow<ResultSource<SpeechImplD>>
}