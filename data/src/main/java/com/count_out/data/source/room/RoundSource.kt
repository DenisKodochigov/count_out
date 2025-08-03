package com.count_out.data.source.room

import com.count_out.data.models.RoundImpl
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface RoundSource {
    fun get(round: TypeSource): Flow<ResultSource<TypeSource>>
    fun gets(trainingId: TypeSource): Flow<ResultSource<TypeSource>>
    fun del(round: TypeSource): ResultSource<TypeSource>
    fun copy(round: TypeSource): ResultSource<TypeSource>
    fun update(round: TypeSource): ResultSource<TypeSource>
}