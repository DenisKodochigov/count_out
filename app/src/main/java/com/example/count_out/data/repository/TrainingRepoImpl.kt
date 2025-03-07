package com.example.count_out.data.repository

import com.example.count_out.domain.repository.trainings.TrainingRepo
import com.example.count_out.data.source.room.RoundSource
import com.example.count_out.data.source.room.TrainingSource
import com.example.count_out.entity.models.TrainingImpl
import com.example.count_out.entity.workout.Training
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingRepoImpl @Inject constructor(private val trainingSource: TrainingSource): TrainingRepo {
    override fun get(id: Long): Flow<Training> = trainingSource.get(id)

    override fun gets(): Flow<List<Training>> = trainingSource.gets()

    override fun del(training: Training): Flow<List<Training>> {
        trainingSource.del(training as TrainingImpl)
        return trainingSource.gets()
    }
    override fun add(): Flow<List<Training>> {
        return trainingSource.add()
    }
    override fun copy(training: Training): Flow<List<Training>> {
//        trainingSource.copy(training as TrainingImpl)
        return trainingSource.copy(training as TrainingImpl)
    }

    override fun select(training: Training): Flow<List<Training>> {
        trainingSource.update(training as TrainingImpl)
        return trainingSource.gets()
    }

    override fun update(training: Training): Flow<Training> {
        trainingSource.update(training as TrainingImpl)
        return trainingSource.get(training.idTraining)
    }
}