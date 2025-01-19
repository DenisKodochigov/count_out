package com.example.domain.repository.training

import com.example.domain.entity.Round
import kotlinx.coroutines.flow.Flow

interface RoundRepo {
    fun get(id: Long): Flow<Round>
    fun gets(trainingId: Long): Flow<List<Round>>
    fun update(round: Round): Flow<Round>
}