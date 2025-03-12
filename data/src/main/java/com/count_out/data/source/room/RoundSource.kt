package com.count_out.data.source.room

import com.count_out.domain.entity.workout.Round
import kotlinx.coroutines.flow.Flow

interface RoundSource {
    fun get(round: Round): Flow<Round>
    fun gets(trainingId: Long): Flow<List<Round>>
    fun del(round: Round)
    fun copy(round: Round): Long
    fun update(round: Round)
}