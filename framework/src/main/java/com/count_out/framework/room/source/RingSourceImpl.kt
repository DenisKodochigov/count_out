package com.count_out.framework.room.source

import com.count_out.data.models.ExerciseDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Companion.asType
import com.count_out.data.models.throwable.ResultSource.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.result
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTb
import javax.inject.Inject

/**
 * Добавляем раунд только когда создаем новый тренировочный план. Поэтому эта функция не появляется в Repo
 * Удаляем раунд только когда удалякм тренировочный план. Поэтому эта функция не появляется в Repo
 */
class RingSourceImpl @Inject constructor(
    private val dao: RingDao,
    private val source: ExerciseSource,
    private val speechSource: SpeechSourceImpl,
): RingSource, PrimeSource() {

    override fun copy(ring: TypeSource): ResultSource<TypeSource> =
        ring.useResult { ringTb->
            var idNew = TypeSource.LongT(0L)
            dao.insert(ringTb.copy(idRing = 0L)).result()
                .flatMap { ownerId ->
                    idNew = ownerId
                    val listSpeech = speechSource.getListSpeech( ringId = ringTb.idRing)
                        .map { it.apply { ringId = ownerId.item } }
                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
                        ResultSource.Success(TypeSource.IntT(listSpeech.count()))
                    else ResultSource.Error(ThrowableDS.RequestFailed()) }
                .flatMap { copyExercises(ringTb.exercises, ownerId = idNew) }
        }

    override fun del(ring: TypeSource): ResultSource<TypeSource> =
        ring.use { dao.delete( it).toLong() }

    override fun update(ring: TypeSource): ResultSource<TypeSource> =
        ring.use { dao.update(it).toLong() }

//##############################################################################################
    inline fun TypeSource.use(crossinline block: (RingTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.RingT) {
            try { block(this.item as RingTb).result() }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    inline fun TypeSource.useResult(crossinline block: (RingTb) -> ResultSource<TypeSource>): ResultSource<TypeSource> =
        if (this is TypeSource.RingT) {
            try { block(this.item as RingTb) }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    fun copyExercises(exercises: List<ExerciseDb>, ownerId: TypeSource.LongT): ResultSource<TypeSource> =
        if (exercises.isEmpty()) { ResultSource.Success(TypeSource.IntT(0)) }
        else {
            exercises.map {ex-> source.copy(TypeSource.ExerciseT(
                (ex as ExerciseTb).apply{ this.ringId = ownerId.item})) }
                .firstOrNull {it is ResultSource.Error}
                ?: ResultSource.Success(TypeSource.IntT(exercises.size))
        }

}
