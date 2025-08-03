package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface SpeechSource {
    fun get(id: TypeSource): Flow<ResultSource<TypeSource>>
    fun copy(speech: TypeSource): ResultSource<TypeSource>
    fun del(speech: TypeSource): ResultSource<TypeSource>
    fun update(speech: TypeSource): ResultSource<TypeSource>
}
//    fun get(id: Long): Flow<SpeechImplD?>
//    fun copy(speech: SpeechImplD): Long?
//    fun update(speech: SpeechImplD)
////    fun updateDuration(speech: SpeechImpl): Flow<Speech>
//    fun del(id: Long)
//    fun copy(speech: TypeSource): Flow<ResultSource<TypeSource>>
//    fun del(speech: TypeSource): Flow<ResultSource<TypeSource>>
//    fun update(speech: TypeSource): Flow<ResultSource<TypeSource>>