package com.count_out.framework.room.source

import android.database.sqlite.SQLiteConstraintException
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.RoundImpl
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RoundSource
import com.count_out.framework.room.db.round.RoundDao
import com.count_out.framework.room.db.round.RoundTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Добавляем раунд только когда создаем новый тренировочный план. Поэтому эта функция не появляется в Repo
 * Удаляем раунд только когда удалякм тренировочный план. Поэтому эта функция не появляется в Repo
 */
class RoundSourceImpl @Inject constructor(
    private val dao: RoundDao,
    private val source: ExerciseSource,
    private val speechKitSource: SpeechKitSourceImpl,
): RoundSource, PrimeSource() {
    override fun gets(trainingId: TypeSource): Flow<ResultSource<TypeSource>> =
        try {
            if (trainingId is TypeSource.LongT) {
                dao.gets(trainingId.item).filterNotNull().map { list ->
                    TypeSource.RoundsT(list.map { it.toRound() }) }.resultSource()
            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
        } catch(e: SQLiteConstraintException) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }

    override fun get(round: TypeSource): Flow<ResultSource<TypeSource>> =
        try {
            if (round is TypeSource.RoundT) {
                dao.get(round.item.idRound).filterNotNull().map { TypeSource.RoundT(it.toRound()) }.resultSource()
            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
        } catch(e: SQLiteConstraintException) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }

    override fun copy(round: TypeSource): ResultSource<TypeSource> {
        return try {
            if (round is TypeSource.RoundT) {
                val speechKitTypeSource = TypeSource.SpeechKitT(
                    round.item.speech?.let { it as SpeechKitImplD } ?: SpeechKitImplD())
                speechKitSource.copy(speechKitTypeSource).result { idSpeechKit->
                    if (idSpeechKit is TypeSource.LongT) {
                        dao.add(RoundTable(RoundImpl(round.item), idSpeechKit.item,0L))
                            .let{id->
                                if (id == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
                                else if (round.item.exercise.isNotEmpty()){
                                    var error = false
                                    round.item.exercise.forEach { exercise ->
                                        source.copy(
                                            TypeSource.ExerciseT(
                                                ExerciseImplD(exercise))).let{
                                            if (it is ResultSource.Error) {
                                                error = true
                                                return@forEach
                                            } }
                                    }
                                    if (error) ResultSource.Error(ThrowableDS.RequestFailed())
                                    else ResultSource.Success(TypeSource.LongT(id))
                                } else source.copy(
                                    TypeSource.ExerciseT(ExerciseImplD(ringId = id)))
                            }
                    }
                    else ResultSource.Error(ThrowableDS.NotValidType())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())

        } catch (e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun del(round: TypeSource): ResultSource<TypeSource> {
        return try {
            if (round is TypeSource.RoundT) {
                var error = false
                round.item.speech?.let {
                    speechKitSource.del(TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                if (round.item.exercise.isNotEmpty()){
                    round.item.exercise.forEach { exercise ->
                        source.del(TypeSource.ExerciseT(
                            ExerciseImplD(exercise))).let { result->
                            if (result is ResultSource.Error) {
                                error = true
                                return@forEach
                            } }
                    }
                }
                if (error)ResultSource.Error(ThrowableDS.RequestFailed())
                else {
                    dao.del(round.item.idRound).let{result->
                        if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                        else ResultSource.Success(TypeSource.IntT(result))
                    }
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun update(round: TypeSource): ResultSource<TypeSource> {
        return try {
            if (round is TypeSource.RoundT) {
                round.item.speech?.let {speechKitSource.update(
                    TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                dao.update(RoundTable(RoundImpl(round.item))).let{result->
                    if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                    else ResultSource.Success(TypeSource.IntT(result))
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
}