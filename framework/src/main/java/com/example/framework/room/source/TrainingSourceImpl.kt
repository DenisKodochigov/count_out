package com.example.framework.room.source

import com.example.data.entity.RoundImpl
import com.example.data.entity.SpeechKitImpl
import com.example.data.entity.TrainingImpl
import com.example.data.source.room.TrainingSource
import com.example.domain.entity.enums.RoundType
import com.example.framework.room.db.training.TrainingDao
import com.example.framework.room.db.training.TrainingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingSourceImpl @Inject constructor(
    private val roundSource: RoundSourceImpl,
    private val speechKitSource: SpeechKitSourceImpl,
    private val dao: TrainingDao): TrainingSource {

    override fun addCopy(training: TrainingImpl): Flow<List<TrainingImpl>> {
        val idSpeechKit = (speechKitSource.add(training.speech as SpeechKitImpl) as StateFlow).value.idSpeechKit
        val trainingId = (dao.add(toTrainingTable(training.copy(speechId = idSpeechKit))))
        if (training.rounds.isNotEmpty()) {
            training.rounds.forEach { round->
                roundSource.addCopy((round as RoundImpl).copy(trainingId = trainingId)) }
        } else {
            roundSource.addCopy( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkUp))
            roundSource.addCopy( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkOut))
            roundSource.addCopy( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkDown))
        }
        return gets()
    }

    override fun update(training: TrainingImpl): Flow<TrainingImpl> {
        dao.update(toTrainingTable(training))
        return get(training.idTraining)
    }

    override fun gets(): Flow<List<TrainingImpl>> =
        dao.getTrainingsRel().map { list-> list.map { item -> item.toTraining() } }

    override fun get(id: Long): Flow<TrainingImpl> = dao.getTrainingRel(id).map { it.toTraining() }

    override fun del(training: TrainingImpl) {
        training.rounds.forEach { roundSource.del(it as RoundImpl) }
        training.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(training.idTraining)
    }

    private fun toTrainingTable(training: TrainingImpl) =
        TrainingTable(
            idTraining = training.idTraining,
            name = training.name,
            speechId = training.speechId
        )

    private fun newRoundImpl(trainingId: Long, roundType: RoundType) = RoundImpl(
        exercise = emptyList(),
        idRound = 0,
        roundType = roundType,
        speechId = 0,
        speech = null,
        trainingId = trainingId,
    )
}