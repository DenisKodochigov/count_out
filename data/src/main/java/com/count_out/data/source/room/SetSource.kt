package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun copy( set: TypeSource): ResultSource<TypeSource>
    fun del( set: TypeSource): ResultSource<TypeSource>
    fun update( set: TypeSource): ResultSource<TypeSource>
}