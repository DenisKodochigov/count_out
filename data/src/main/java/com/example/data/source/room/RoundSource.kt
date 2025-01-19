package com.example.data.source.room

import com.example.data.entity.RoundImpl
import com.example.domain.entity.Round
import kotlinx.coroutines.flow.Flow

interface RoundSource {
    fun get(id: Long): Flow<Round>
    fun gets(trainingId: Long): Flow<List<Round>>
    fun del(round: RoundImpl)
    fun addCopy(round: RoundImpl): Flow<List<RoundImpl>>
    fun update(round: RoundImpl): Flow<Round>
}