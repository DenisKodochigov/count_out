package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface ActivitySource {
    fun gets(): Flow<ResultSource<TypeSource>>
    fun get(activity: TypeSource): Flow<ResultSource<TypeSource>>
    fun copy(activity: TypeSource): ResultSource<TypeSource>
    fun update(activity: TypeSource): ResultSource<TypeSource>
    fun del(id: TypeSource): ResultSource<TypeSource>
}
