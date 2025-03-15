package com.count_out.framework.room.source

import com.count_out.data.models.RingImpl
import com.count_out.data.models.RoundImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.models.TrainingImpl
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.workout.Training
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.db.training.TrainingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingSourceImpl @Inject constructor(
    private val dao: TrainingDao,
    private val roundSource: RoundSource,
    private val ringSource: RingSource,
    private val speechKitSource: SpeechKitSource,
): TrainingSource {

    override fun update(training: Training){
        training.speech?.let { speechKitSource.update(it) }
        training.rounds.forEach { round-> roundSource.update(round) }
        dao.update(toTrainingTable(training as TrainingImpl))
    }

    override fun copy(training: Training): Long {
        val speechId = speechKitSource.copy(
            training.speech?.let{ it as SpeechKitImpl} ?: SpeechKitImpl())
        val trainingId = ( dao.add(
            TrainingTable(
//                idTraining = 0,
                name = training.name,
                speechId = speechId
            )))
        if (training.rounds.isNotEmpty()) {
            training.rounds.forEach { round->
                roundSource.copy((round as RoundImpl).copy(trainingId = trainingId)) }
        } else {
            roundSource.copy( RoundImpl(trainingId = trainingId, roundType = RoundType.WorkUp))
            roundSource.copy( RoundImpl(trainingId = trainingId, roundType = RoundType.WorkOut))
            roundSource.copy( RoundImpl(trainingId = trainingId, roundType = RoundType.WorkDown))
        }
        return trainingId
    }

    override fun gets(): Flow<List<TrainingImpl>> =
        dao.getTrainingsRel().map { list-> list.map { item -> item.toTraining() } }

    override fun get(training: Training): Flow<TrainingImpl> =
        dao.getTrainingRel(training.idTraining).map { it.toTraining() }

    override fun del(training: Training) {
        training.rounds.forEach { roundSource.del(it as RoundImpl) }
        training.rings.forEach { ringSource.del(it as RingImpl) }
        training.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(training.idTraining)
    }

    private fun toTrainingTable(training: Training) =
        TrainingTable(
//            idTraining = training.idTraining,
            name = training.name,
            speechId = training.speechId
        )
}