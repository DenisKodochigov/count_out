package com.example.count_out.domain.repository.trainings

import com.example.count_out.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRepo {
    fun get(id: Long): Flow<Training>
    fun gets(): Flow<List<Training>>
    fun del(training: Training): Flow<List<Training>>
    fun add(): Flow<List<Training>>
    fun copy(training: Training): Flow<List<Training>>
    fun select(training: Training): Flow<List<Training>>
    fun update(training: Training): Flow<Training>
//    override fun <Training> gets(): Flow<List<Training>>
//    override fun <T> get(): Flow<T>
//    override fun <Training> add(item: Training): Flow<Training>
//    override fun <Training> copy(id: Long): Flow<Training>
//    override fun <Training> update(item: Training): Flow<Training>
//    override fun del(id: Long)
}