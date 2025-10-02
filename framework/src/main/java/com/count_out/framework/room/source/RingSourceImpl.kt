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
    private val speechKitSource: SpeechKitSourceImpl,
): RingSource, PrimeSource() {

    override fun copy(ring: TypeSource): ResultSource<TypeSource> =
        (ring as? TypeSource.RingT)?.let { ring ->
            speechKitSource.insert(ring.item.speechId ?: 0).asType<TypeSource.LongT>()
            .flatMap { idSpeechKit ->
                val obj = (ring.item as RingTb).apply{this.speechId = idSpeechKit.item; this.idRing = 0L }
                dao.insert(obj).result()
            }
            .flatMap { ownerId->
                if (ownerId.item == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
                else copyExercises(ring.item.exercises, ownerId) }
        } ?: ResultSource.Error(ThrowableDS.NotValidType())

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

    fun copyExercises(exercises: List<ExerciseDb>, ownerId: TypeSource.LongT): ResultSource<TypeSource> =
        if (exercises.isEmpty()) { ResultSource.Success(TypeSource.IntT(0)) }
        else {
            exercises.map {ex-> source.copy(TypeSource.ExerciseT(
                (ex as ExerciseTb).apply{ this.ringId = ownerId.item})) }
                .firstOrNull {it is ResultSource.Error}
                ?: ResultSource.Success(TypeSource.IntT(exercises.size))
        }
}
