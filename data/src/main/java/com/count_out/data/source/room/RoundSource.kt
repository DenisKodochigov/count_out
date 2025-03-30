package com.count_out.data.source.room

import com.count_out.data.models.RoundImpl
import kotlinx.coroutines.flow.Flow

interface RoundSource {
    fun get(round: RoundImpl): Flow<RoundImpl?>
    fun gets(trainingId: Long): Flow<List<RoundImpl>>
    fun del(round: RoundImpl)
    fun copy(round: RoundImpl): Long
    fun update(round: RoundImpl)
}