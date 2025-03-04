package com.count_out.data.source.room

import com.count_out.data.models.RoundImpl
import com.count_out.entity.entity.workout.Round
import kotlinx.coroutines.flow.Flow

interface RoundSource {
    fun get(id: Long): Flow<Round>
    fun gets(trainingId: Long): Flow<List<Round>>
    fun del(round: RoundImpl)
    fun add(round: RoundImpl): Flow<List<RoundImpl>>
    fun copy(round: RoundImpl): Flow<List<RoundImpl>>
    fun update(round: RoundImpl): Flow<Round>
}