package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface TrainingSource {
    fun gets(): Flow<ResultSource<TypeSource>>
    fun get(training: TypeSource): Flow<ResultSource<TypeSource>>
    fun getId(id: TypeSource): Flow<ResultSource<TypeSource>>
    fun copy(training: TypeSource): ResultSource<TypeSource>
    fun update(training: TypeSource): ResultSource<TypeSource>
    fun del(training: TypeSource): ResultSource<TypeSource>
}