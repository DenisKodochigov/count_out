package com.count_out.domain.repository.plans

import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRepo {
    fun get(training: Training): Flow<Training>
    fun gets(): Flow<List<Training>>
    fun del(training: Training): Flow<List<Training>>
    fun copy(training: Training): Flow<List<Training>>
    fun select(training: Training): Flow<List<Training>>
    fun update(training: Training): Flow<Training>
    fun updates(training: Training): Flow<List<Training>>
}