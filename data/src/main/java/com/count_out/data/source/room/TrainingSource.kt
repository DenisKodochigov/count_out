package com.count_out.data.source.room

import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingSource {
    fun gets(): Flow<List<Training>>
    fun get(training: Training): Flow<Training?>
    fun copy(training: Training): Long
    fun update(training: Training)
    fun del(training: Training)
}