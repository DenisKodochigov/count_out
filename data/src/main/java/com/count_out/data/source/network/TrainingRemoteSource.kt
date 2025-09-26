package com.count_out.data.source.network

import com.count_out.domain.entity.workout.Plan
import kotlinx.coroutines.flow.Flow


interface TrainingRemoteSource {
    fun getTraining(id: Long): Flow<Plan>
    fun addTraining(training: Plan): Flow<Plan>
    fun updateTraining(training: Plan): Flow<Plan>
    fun delTraining(id: Long)
}