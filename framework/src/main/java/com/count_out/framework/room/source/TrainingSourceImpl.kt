package com.count_out.framework.room.source

import com.count_out.data.models.RingImpl
import com.count_out.data.models.RoundImpl
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.enums.RoundType
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.db.training.TrainingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingSourceImpl @Inject constructor(
    private val dao: TrainingDao,
    private val roundSource: RoundSource,
    private val ringSource: RingSource,
    private val speechKitSource: SpeechKitSourceImpl,
): TrainingSource {

    override fun update(training: TrainingImplD) {
        training.speech?.let { speechKitSource.update(SpeechKitImplD(it)) }
        training.rounds.forEach { round -> roundSource.update(round as RoundImpl) }
        dao.update(TrainingTable(training))
    }

    override fun copy(training: TrainingImplD): Long {
        val speechId = speechKitSource.copyValue(
            training.speech?.let { it as SpeechKitImplD } ?: SpeechKitImplD()) ?: 0L
        val trainingId = (dao.add(TrainingTable(name = training.name, speechId = speechId))) ?: 0
        if (trainingId > 0){
            if (training.rounds.isNotEmpty()) {
                training.rounds.forEach { round ->
                    roundSource.copy((round as RoundImpl).copy(trainingId = trainingId))
                }
            } else {
                roundSource.copy(RoundImpl(trainingId = trainingId, roundType = RoundType.WorkUp))
                roundSource.copy(RoundImpl(trainingId = trainingId, roundType = RoundType.WorkOut))
                roundSource.copy(RoundImpl(trainingId = trainingId, roundType = RoundType.WorkDown))
            }
        }
        return trainingId
    }

    override fun gets(): Flow<List<TrainingImplD>> {
        return dao.getTrainingsRel().map { list -> list.map { item -> item.toTraining() } }
    }

    override fun get(training: TrainingImplD): Flow<TrainingImplD?> {
        return dao.getTrainingRel(training.idTraining).map { it?.toTraining() }
    }

    override fun del(training: TrainingImplD) {
        training.rounds.forEach { roundSource.del(it as RoundImpl) }
        training.rings.forEach { ringSource.del(it as RingImpl) }
        training.speech?.let { speechKitSource.del(it as SpeechKitImplD) }
        dao.del(training.idTraining)
    }
}