package com.count_out.domain.repository.trainings

import com.example.count_out.entity.workout.Round
import kotlinx.coroutines.flow.Flow

interface RoundRepo {
    fun get(id: Long): Flow<Round>
    fun gets(trainingId: Long): Flow<List<Round>>
    fun update(round: Round): Flow<Round>
}