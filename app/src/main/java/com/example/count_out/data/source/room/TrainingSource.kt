package com.example.count_out.data.source.room

import com.example.count_out.entity.models.TrainingImpl
import com.example.count_out.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingSource {
    fun add(): Flow<List<Training>>
    fun copy(training: Training): Flow<List<Training>>
    fun gets(): Flow<List<Training>>
    fun get(id: Long): Flow<Training>
    fun update(training: Training): Flow<Training>
    fun del(training: Training)
//    override fun <Training> gets(): Flow<List<Training>>
//    override fun <Training> get(): Flow<Training>
//    override fun <Training> add(item: Training): Flow<Training>
//    override fun <Training> copy(id: Long): Flow<Training>
//    override fun <Training> update(item: Training): Flow<Training>
//    override fun del(id: Long)
}