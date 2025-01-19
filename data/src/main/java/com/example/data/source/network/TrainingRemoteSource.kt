package com.example.data.source.network

import com.example.domain.entity.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRemoteSource {
    fun getTraining(id: Long): Flow<Training>
    fun addTraining(training: Training): Flow<Training>
    fun updateTraining(training: Training): Flow<Training>
    fun delTraining(id: Long)
}