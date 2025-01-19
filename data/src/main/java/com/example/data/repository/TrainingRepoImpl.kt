package com.example.data.repository

import com.example.data.entity.TrainingImpl
import com.example.data.source.room.RoundSource
import com.example.data.source.room.TrainingSource
import com.example.domain.entity.Training
import com.example.domain.repository.training.TrainingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainingRepoImpl @Inject constructor(
    private val trainingSource: TrainingSource,
    private val roundSource: RoundSource): TrainingRepo
{
    override fun get(id: Long): Flow<Training> = trainingSource.get(id)

    override fun gets(): Flow<List<Training>> = trainingSource.gets()

    override fun del(id: Long): Flow<List<Training>> {
        trainingSource.del(id)
        return trainingSource.gets()
    }

    override fun add(training: Training): Flow<List<Training>> {
        val idTraining = trainingSource.add(training as TrainingImpl)
//        roundSource.add(idTraining)
        return trainingSource.gets()
    }

    override fun copy(id: Long): Flow<List<Training>> {
        TODO("Not yet implemented")
    }

    override fun update(training: Training): Flow<Training> {
        trainingSource.update(training as TrainingImpl)
        return trainingSource.get(training.idTraining)
    }


}