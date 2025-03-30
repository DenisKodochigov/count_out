package com.count_out.data.source.room

import com.count_out.data.models.TrainingImplD
import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingSource {
    fun gets(): Flow<List<TrainingImplD>>
    fun get(training: TrainingImplD): Flow<TrainingImplD?>
    fun copy(training: TrainingImplD): Long
    fun update(training: TrainingImplD)
    fun del(training: TrainingImplD)
}