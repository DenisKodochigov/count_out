package com.example.count_out.framework.room.source

import com.example.count_out.data.source.room.RingSource
import com.example.count_out.data.source.room.RoundSource
import com.example.count_out.data.source.room.SpeechKitSource
import com.example.count_out.data.source.room.TrainingSource
import com.example.count_out.entity.enums.RoundType
import com.example.count_out.entity.enums.Units
import com.example.count_out.entity.models.ParameterImpl
import com.example.count_out.entity.models.RoundImpl
import com.example.count_out.entity.models.SpeechKitImpl
import com.example.count_out.entity.models.TrainingImpl
import com.example.count_out.entity.workout.Training
import com.example.count_out.framework.room.db.training.TrainingDao
import com.example.count_out.framework.room.db.training.TrainingTable
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingSourceImpl @Inject constructor(
    private val dao: TrainingDao,
    private val roundSource: RoundSource,
    private val ringSource: RingSource,
    private val speechKitSource: SpeechKitSource,): TrainingSource {

    override fun update(training: Training): Flow<Training> {
        dao.update(toTrainingTable(training))
        return get(training.idTraining)
    }

    override fun add(): Flow<List<Training>> {
        val trainingId = ( dao.add( TrainingTable(
            name = "Training",
            speechId = speechKitSource.add(null))))
        roundSource.add( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkUp))
        roundSource.add( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkOut))
        roundSource.add( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkDown))
        return gets()
    }

    override fun copy(training: Training): Flow<List<Training>> {
        val idSpeechKit = speechKitSource.copy(training.speech as SpeechKitImpl)
        val trainingId = (dao.add(toTrainingTable((training as TrainingImpl).copy(speechId = idSpeechKit))))
        if (training.rounds.isNotEmpty()) {
            training.rounds.forEach { round->
                roundSource.copy((round as RoundImpl).copy(trainingId = trainingId)) }
        } else {
            roundSource.copy( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkUp))
            roundSource.copy( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkOut))
            roundSource.copy( newRoundImpl(trainingId = trainingId, roundType = RoundType.WorkDown))
        }
        return gets()
    }

    override fun gets(): Flow<List<Training>> {
        return  flow { emit(dao.getTrainingsRel().map { item -> item.toTraining() }) }
    }

    override fun get(id: Long): Flow<Training> = flow{ emit(dao.getTrainingRel(id).toTraining())}

    override fun del(training: Training) {
        training.rounds.forEach { roundSource.del(it as RoundImpl) }
        training.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(training.idTraining)
    }

    private fun toTrainingTable(training: Training) =
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
        amount = 0,
        duration = ParameterImpl(value = 0.0, unit = Units.M),
    )
}