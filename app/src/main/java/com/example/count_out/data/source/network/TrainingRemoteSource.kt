package com.example.count_out.data.source.network

import com.example.count_out.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRemoteSource {
    fun getTraining(id: Long): Flow<Training>
    fun addTraining(training: Training): Flow<Training>
    fun updateTraining(training: Training): Flow<Training>
    fun delTraining(id: Long)
}