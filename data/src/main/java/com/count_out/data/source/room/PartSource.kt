package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource

interface PartSource {
    fun del(part: TypeSource): ResultSource<TypeSource>
    fun copy(part: TypeSource): ResultSource<TypeSource>
    fun update(part: TypeSource): ResultSource<TypeSource>
}
//    fun get(round: TypeSource): Flow<ResultSource<TypeSource>>
//    fun gets(trainingId: TypeSource): Flow<ResultSource<TypeSource>>