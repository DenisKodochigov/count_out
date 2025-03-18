package com.count_out.data.source.room

import com.count_out.data.models.SetImplD
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun gets(exerciseId: Long): Flow<List<SetImplD>>
    fun get( item: SetImplD): Flow<SetImplD>
    fun copy( item: SetImplD): Long
    fun del( item: SetImplD)
    fun update( item: SetImplD)
}