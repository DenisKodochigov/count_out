package com.example.data.source.room

import com.example.data.entity.TrainingImpl
import com.example.domain.entity.Training
import kotlinx.coroutines.flow.Flow

interface TrainingSource {
    fun add(): Flow<List<Training>>
    fun copy(training: TrainingImpl): Flow<List<Training>>
    fun gets(): Flow<List<Training>>
    fun get(id: Long): Flow<Training>
    fun update(training: TrainingImpl): Flow<Training>
    fun del(training: TrainingImpl)
//    override fun <Training> gets(): Flow<List<Training>>
//    override fun <Training> get(): Flow<Training>
//    override fun <Training> add(item: Training): Flow<Training>
//    override fun <Training> copy(id: Long): Flow<Training>
//    override fun <Training> update(item: Training): Flow<Training>
//    override fun del(id: Long)
}