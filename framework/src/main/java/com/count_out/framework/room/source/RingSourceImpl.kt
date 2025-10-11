package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
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

    override fun copy (ring: Data): ResultData<Data> = runCatching {
        copyWithDependencies(
            original = (ring as RingTb),
            originalId = ring.idRing,
            insertMain = { dao.insert(it.copy(idRing = 0L)) },
            getSpeeches = { oldId -> speechSource.getListSpeech(ringId = oldId) },
            insertSpeeches = { speechSource.insert(it) },
            copyNested = { id -> copyExercises(ring.exercises,id)}
        )
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, Long> { dao.delete( it).toLong() }

    override fun update(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, Long> { dao.update(it).toLong() }

//##############################################################################################
    fun copyExercises(exercises: List<ExerciseDb>, id: Long): ResultData<LongDb> =
        if (exercises.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            exercises.map{ex-> source.copy(
                (ex as ExerciseTb).apply{this.ringId = id}) }
                ResultData.Success(LongDb(exercises.size.toLong()))
        }
}
//dao.insert(ringTb.copy(idRing = 0L)).longToResult()
//.flatMap { ownerId ->
//    idNew = ownerId as LongDb
//    val listSpeech = speechSource.getListSpeech( ringId = ringTb.idRing)
//        .map { it.apply { ringId = ownerId.item } }
//    if (speechSource.insert(listSpeech).count() == listSpeech.count())
//        ResultData.Success(LongDb(listSpeech.count().toLong()))
//    else ResultData.Error(ThrowableDS.RequestFailed()) }
//.flatMap { copyExercises(ringTb.exercises, ownerId = idNew) }



//        val newId = dao.insert(ringTb.copy(idRing = 0L))
//            .takeIf { it != 0L } ?: return ResultData.Error(ThrowableDS.RequestFailed())
//
//        val speeches = speechSource.getListSpeech( ringId = ringTb.idRing)
//            .map { it.apply { ringId = newId } as SpeechTb }
//        if (speeches.isEmpty()) return ResultData.Error(ThrowableDS.RequestFailed())
//
//        if (speechSource.insert(speeches).count() != speeches.count())
//            return ResultData.Error(ThrowableDS.RequestFailed())
//
//        if (copyExercises(ring.exercises, ownerId = LongDb(newId)) is ResultData.Error)
//            return ResultData.Error(ThrowableDS.RequestFailed())
//
//        when (copyExercises(ring.exercises, ownerId = LongDb(newId))) {
//            is ResultData.Success -> ResultData.Success(BooleanDb(true))
//            else -> ResultData.Error(ThrowableDS.RequestFailed())
//        }


//    override fun copy(ring: Data): ResultData<Data> = (ring as? RingTb)?.let { ringTb ->
//        val newId = dao.insert(ringTb.copy(idRing = 0L))
//        if (newId == 0L) return ResultData.Error(ThrowableDS.RequestFailed())
//        val speeches = speechSource.getListSpeech( ringId = ringTb.idRing)
//            .map { it.apply { ringId = newId } as SpeechTb }
//        if (speeches.isEmpty()) return ResultData.Error(ThrowableDS.RequestFailed())
//
//        if (speechSource.insert(speeches).count() != speeches.count())
//            return ResultData.Error(ThrowableDS.RequestFailed())
//
//        if (copyExercises(ring.exercises, ownerId = LongDb(newId)) is ResultData.Error)
//            return ResultData.Error(ThrowableDS.RequestFailed())
//
//        when (copyExercises(ring.exercises, ownerId = LongDb(newId))) {
//            is ResultData.Success -> ResultData.Success(BooleanDb(true))
//            else -> ResultData.Error(ThrowableDS.RequestFailed())
//        }
//    } ?: ResultData.Error(ThrowableDS.NotValidType())

//    override fun copy(ring: Data): ResultData<Data> = (ring as? RingTb)?.let { ringTb ->
//        val newId = dao.insert(ringTb.copy(idRing = 0L))
//        newId.longToResult { LongDb(it) }
//            .flatMapCondition<LongDb, SpeechesDb>({ it.item.isNotEmpty() }) { newId1 ->
//                speechSource.getListSpeech(ringId = ringTb.idRing)
//                    .map { it.apply { ringId = newId1.item } as SpeechTb }
//                    .let { SpeechesDb(item = it).toResultData() }
//            }
//            .flatMapCondition({vl-> vl.item.isNotEmpty()}){speeches-> ///<SpeechesDb, LongesDb>
//                speechSource.insert(speeches.item.map { it as SpeechTb })
//                    .listToResult({ LongesDb(it) })}
//            .flatMapCondition({ vl -> vl.item > 0L }) {  //<LongesDb, LongDb>
//                copyExercises(ringTb.exercises, ringId = newId) }
//        } ?: ResultData.Error(ThrowableDS.NotValidType())