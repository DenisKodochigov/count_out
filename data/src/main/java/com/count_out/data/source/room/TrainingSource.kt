package com.count_out.data.source.room

import com.count_out.data.models.TrainingImpl
import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingSource {
    fun gets(): Flow<List<TrainingImpl>>
    fun get(training: Training): Flow<TrainingImpl>
    fun copy(training: Training): Long
    fun update(training: Training)
    fun del(training: Training)
}