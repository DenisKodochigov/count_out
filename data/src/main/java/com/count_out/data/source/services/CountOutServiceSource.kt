package com.count_out.data.source.services

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface CountOutServiceSource {
    fun bind(): Flow<ResultSource<TypeSource>>
    fun unbind(): Flow<ResultSource<TypeSource>>
}