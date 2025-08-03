package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun get(speechKit: TypeSource): Flow<ResultSource<TypeSource>>
    fun copy(speechKit: TypeSource): ResultSource<TypeSource>
    fun update(speechKit: TypeSource): ResultSource<TypeSource>
    fun del(speechKit: TypeSource): ResultSource<TypeSource>
}