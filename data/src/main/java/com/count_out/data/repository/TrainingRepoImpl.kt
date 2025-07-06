package com.count_out.data.repository

import com.count_out.data.models.TrainingImplD
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.plans.TrainingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class TrainingRepoImpl @Inject constructor(private val trainingSource: TrainingSource): TrainingRepo {

    override fun get(training: Training): Flow<Training> {
        return trainingSource.get(TrainingImplD(training)).filterNotNull()
    }
    override fun gets(): Flow<List<Training>> {
        return trainingSource.gets() }

    override fun del(training: Training): Flow<List<Training>> {
        trainingSource.del(TrainingImplD(training))
        return trainingSource.gets()
    }
    override fun copy(training: Training): Flow<List<Training>> {
        trainingSource.copy(TrainingImplD(training))
        return trainingSource.gets()
    }

    override fun select(training: Training): Flow<List<Training>> {
        trainingSource.update(TrainingImplD(training))
        return trainingSource.gets()
    }
    override fun update(training: Training): Flow<Training> {
        trainingSource.update(TrainingImplD(training) )
        return trainingSource.get(TrainingImplD(training)).filterNotNull()
    }
    override fun updates(training: Training): Flow<List<Training>> {
        trainingSource.update(TrainingImplD(training) )
        return trainingSource.gets().filterNotNull()
    }
}