package com.example.count_out.data.source.room

import com.example.count_out.entity.workout.Round
import kotlinx.coroutines.flow.Flow

interface RoundSource {
    fun get(id: Long): Flow<Round>
    fun gets(trainingId: Long): Flow<List<Round>>
    fun del(round: Round)
    fun add(round: Round): Flow<List<Round>>
    fun copy(round: Round): Flow<List<Round>>
    fun update(round: Round): Flow<Round>
}