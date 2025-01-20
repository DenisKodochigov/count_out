package com.example.framework.room.source

import android.util.Log
import com.example.data.entity.ExerciseImpl
import com.example.data.entity.RoundImpl
import com.example.data.entity.SpeechKitImpl
import com.example.data.source.room.RoundSource
import com.example.framework.room.db.round.RoundDao
import com.example.framework.room.db.round.RoundTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Добавляем раунд только когда создаем новый тренировочный план. Поэтому эта функция не появляется в Repo
 * Удаляем раунд только когда удалякм тренировочный план. Поэтому эта функция не появляется в Repo
 */
class RoundSourceImpl @Inject constructor(
    private val exerciseSource: ExerciseSourceImpl,
    private val speechKitSource: SpeechKitSourceImpl,
    private val dao: RoundDao
): RoundSource {
    override fun gets(trainingId: Long): Flow<List<RoundImpl>> =
        dao.gets(trainingId).map { list-> list.map { it.toRound() } }

    override fun get(id: Long): Flow<RoundImpl> = dao.get(id).map { it.toRound() }

    override fun addCopy(round: RoundImpl): Flow<List<RoundImpl>> {
        //Создавть раунд имеет смысл только в связке с какимнибудь тренировочным планом.
        if (round.trainingId > 0){
            val idSpeechKit =
                (speechKitSource.add(round.speech as SpeechKitImpl) as StateFlow).value.idSpeechKit
            val roundId = dao.add(toRoundTable(round).copy(speechId = idSpeechKit))
            if (round.exercise.isNotEmpty()) {
                round.exercise.forEach { exercise->
                    exerciseSource.addCopy((exercise as ExerciseImpl).copy(roundId = roundId)) }
            }
            else { exerciseSource.addCopy(createExerciseImpl(roundId = roundId)) }
        } else { Log.d("KDS", "The value is not defined: TRAININGID ")}
        return gets(round.trainingId)
    }

    override fun del(round: RoundImpl) {
        round.exercise.forEach { exerciseSource.del(it as ExerciseImpl) }
        round.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(round.idRound)
    }

    override fun update(round: RoundImpl): Flow<RoundImpl> {
        dao.update(toRoundTable(round).copy(idRound = round.idRound))
        return get(round.idRound)
    }

    private fun toRoundTable(round: RoundImpl) = RoundTable(
        trainingId = round.trainingId,
        speechId = round.speechId,
        roundType = round.roundType.ordinal
    )
    private fun createExerciseImpl(roundId: Long) = ExerciseImpl(
        idExercise = 0,
        roundId = roundId,
        ringId = 0,
        activityId = 1,
        idView = 1,
        activity = null,
        speech = null,
        speechId = 0,
        sets =  emptyList(),
    )
}