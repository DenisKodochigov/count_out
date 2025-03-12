package com.count_out.data.source.room

import com.count_out.data.models.SetImpl
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun gets(exerciseId: Long): Flow<List<SetImpl>>
    fun get( item: SetImpl): Flow<SetImpl>
    fun copy( item: SetImpl): Long
    fun del( item: SetImpl)
    fun update( item: SetImpl)
}