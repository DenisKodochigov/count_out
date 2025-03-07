package com.example.count_out.framework.room.source

import android.util.Log
import com.example.count_out.data.source.room.ExerciseSource
import com.example.count_out.data.source.room.RoundSource
import com.example.count_out.data.source.room.SpeechKitSource
import com.example.count_out.entity.models.ExerciseImpl
import com.example.count_out.entity.models.RoundImpl
import com.example.count_out.entity.models.SpeechKitImpl
import com.example.count_out.entity.workout.Round
import com.example.count_out.framework.room.db.round.RoundDao
import com.example.count_out.framework.room.db.round.RoundTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
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
        flow { emit (dao.gets(trainingId).map { it.toRound() } )}

    override fun get(id: Long): Flow<RoundImpl> = flow { emit (dao.get(id).toRound() )}

    override fun add(round: Round): Flow<List<Round>> {
        //Создавть раунд имеет смысл только в связке с каким нибудь тренировочным планом.
        return if (round.trainingId > 0){
            val roundId = dao.add(RoundTable(speechId = speechKitSource.add(null) ))
            exerciseSource.add(roundId = roundId)
            gets(round.trainingId)
        } else {
            Log.d("KDS", "The value is not defined: TRAININGID ")
            flow { emit(emptyList()) }
        }
    }
    override fun copy(round: Round): Flow<List<Round>> {
        //Создавть раунд имеет смысл только в связке с какимнибудь тренировочным планом.
        if (round.trainingId > 0){
            val idSpeechKit = (round.speech?.let { speechKitSource.copy(it) }) ?: 0
            val roundId = dao.add(toRoundTable(round).copy(speechId = idSpeechKit))
            if (round.exercise.isNotEmpty()) {
                round.exercise.forEach { exercise->
                    exerciseSource.copy((exercise as ExerciseImpl).copy(roundId = roundId)) }
            }
            else { exerciseSource.add(roundId = roundId) }
        } else { Log.d("KDS", "The value is not defined: TRAININGID ")}
        return gets(round.trainingId)
    }
    override fun del(round: Round) {
        round.exercise.forEach { exerciseSource.del(it as ExerciseImpl) }
        round.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(round.idRound)
    }

    override fun update(round: Round): Flow<Round> {
        dao.update(toRoundTable(round).copy(idRound = round.idRound))
        return get(round.idRound)
    }

    private fun toRoundTable(round: Round) = RoundTable(
        trainingId = round.trainingId,
        speechId = round.speechId,
        roundType = round.roundType.ordinal
    )
//    private fun createExerciseImpl(roundId: Long) = ExerciseImpl(
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