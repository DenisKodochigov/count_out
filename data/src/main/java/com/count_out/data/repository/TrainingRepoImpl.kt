package com.count_out.data.repository

import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRepoImpl @Inject constructor(private val trainingSource: TrainingSource): TrainingRepo {

    override fun get(training: Training): Flow<Training> {
        return trainingSource.get(training) }

    override fun gets(): Flow<List<Training>> {
        return trainingSource.gets() }

    override fun del(training: Training): Flow<List<Training>> {
        trainingSource.del(training)
        return trainingSource.gets()
    }
    override fun copy(training: Training): Flow<List<Training>> {
        trainingSource.copy(training)
        return trainingSource.gets()
    }

    override fun select(training: Training): Flow<List<Training>> {
        trainingSource.update(training)
        return trainingSource.gets()
    }

    override fun update(training: Training): Flow<Training> {
        trainingSource.update(training )
        return trainingSource.get(training)
    }
}