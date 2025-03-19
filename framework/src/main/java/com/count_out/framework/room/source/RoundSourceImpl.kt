package com.count_out.framework.room.source

import android.util.Log
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.RoundImpl
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.workout.Round
import com.count_out.framework.room.db.round.RoundDao
import com.count_out.framework.room.db.round.RoundTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Добавляем раунд только когда создаем новый тренировочный план. Поэтому эта функция не появляется в Repo
 * Удаляем раунд только когда удалякм тренировочный план. Поэтому эта функция не появляется в Repo
 */
class RoundSourceImpl @Inject constructor(
    private val dao: RoundDao,
    private val exerciseSource: ExerciseSource,
    private val speechKitSource: SpeechKitSource,
): RoundSource {
    override fun gets(trainingId: Long): Flow<List<RoundImpl>> =
        dao.gets(trainingId).map { list-> list.map { it.toRound() } }

    override fun get(round: Round): Flow<Round> = dao.get(round.idRound).map { it.toRound() }

    override fun copy(round: Round): Long {
        //Создавть раунд имеет смысл только в связке с какимнибудь тренировочным планом.
        var roundId = 0L
        if (round.trainingId > 0){
            val speechId = speechKitSource.copy(round.speech?.let{ it as SpeechKitImplD } ?: SpeechKitImplD() )
            roundId = dao.add(toRoundTable(round as RoundImpl).copy(speechId = speechId))
            if (round.exercise.isNotEmpty()) {
                round.exercise.forEach { exercise->
                    exerciseSource.copy((exercise as ExerciseImplD ).copy(roundId = roundId)) }
            } else { exerciseSource.copy(ExerciseImplD().copy(roundId = roundId)) }
        } else { Log.d("KDS", "The value is not defined: TRAININGID ")}
        return roundId
    }
    override fun del(round: Round) {
        round.exercise.forEach { exerciseSource.del(it) }
        round.speech?.let { speechKitSource.del(it as SpeechKitImplD) }
        dao.del(round.idRound)
    }

    override fun update(round: Round) {
        round.exercise.forEach { exerciseSource.update(it) }
        round.speech?.let { speechKitSource.update(it as SpeechKitImplD) }
        dao.update(toRoundTable(round as RoundImpl))
    }

    private fun toRoundTable(round: RoundImpl) = RoundTable(
//        idRound = round.idRound,
        trainingId = round.trainingId,
        speechId = round.speechId,
        roundType = round.roundType.ordinal
    )
//    private fun createExerciseImpl(roundId: Long) = Exercise(
//        idExercise = 0,
//        roundId = roundId,
//        ringId = 0,
//        activityId = 1,
//        idView = 1,
//        activity = null,
//        speech = null,
//        speechId = 0,
//        sets =  emptyList(),
//    )
}