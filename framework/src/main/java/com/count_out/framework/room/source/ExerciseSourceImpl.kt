package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.SetDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.models.types_data.SetIdViewDb
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.set.SetTb
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechSource: SpeechSourceImpl,
): ExerciseSource, PrimeSource() {

    override fun copy (exercise: Data): ResultData<Data> = runCatching {
        copyWithDependencies(
            original = (exercise as ExerciseTb),
            originalId = exercise.idExercise,
            insertMain = { dao.insert(it.copy(idExercise = 0L)) },
            getSpeeches = { oldId -> speechSource.getListSpeech(exerciseId = oldId) },
            insertSpeeches = { speechSource.insert(it) },
            copyNested = { id -> copySets(exercise.sets,id)}
        )
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    fun copySets(sets: List<SetDb>, ownerId: Long): ResultData<LongDb> =
        if (sets.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            sets.map {set-> setSource.copy((set as SetTb).copy(idSet = 0L, exerciseId = ownerId)) }
                ResultData.Success(LongDb(sets.size.toLong()))
        }

    override fun update(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseTb, Long>{ item -> dao.update(item).toLong() }

    override fun changeSequenceExercise(setViewId: Data): ResultData<Data> {
        return try {
            if (setViewId is SetIdViewDb) {
                val from = setViewId.from
                val to = setViewId.to
                val listExercise = dao.getExerciseRound(setViewId.ringId).toMutableList()
                if (from > to) for ( id in to..< from){ listExercise[id].idView = id + 1 }
                else for ( id in (from + 1)..to){ listExercise[id].idView = id - 1}
                listExercise[from].idView = to
                dao.update(listExercise).let { result->
                    if (result == listExercise.count()) ResultData.Success(LongDb(result.toLong()))
                    else ResultData.Error(ThrowableDS.RequestFailed())
                }
            } else ResultData.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
    }

    override fun del(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseTb, Long>{ item -> dao.delete(item).toLong() }
}
//
//        exercise.safeUse<ExerciseTb, ResultData<Data>> { exerciseTb->
//            var idNew = LongDb(0L)
//            dao.insert(exerciseTb.copy(idExercise = 0L)).longToResult()
//                .flatMap { ownerId->
//                    idNew = ownerId as LongDb
//                    val listSpeech = speechSource.getListSpeech(exerciseId = exerciseTb.idExercise)
//                        .map { it.apply { exerciseId = ownerId.item } }
//                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
//                        ResultData.Success(LongDb(listSpeech.count().toLong()))
//                    else ResultData.Error(ThrowableDS.RequestFailed()) }
//                .flatMap { copySets(exerciseTb.sets, ownerId = idNew) }
//            }
//
//    override fun copy(exercise: Data): ResultData<Data> =
//        (exercise as? ExerciseTb)?.let { exerciseTb ->
//            val newId = dao.insert(exerciseTb.copy(idExercise = 0L))
//            newId.longToResult { LongDb(it) }
//                .flatMapCondition<LongDb, SpeechesDb>({ it.item.isNotEmpty() }) { newId1 ->
//                    speechSource.getListSpeech(ringId = exerciseTb.idExercise)
//                        .map { it.apply { ringId = newId1.item } as SpeechTb }
//                        .let { SpeechesDb(item = it).toResultData() }
//                }
//                .flatMapCondition({vl-> vl.item.isNotEmpty()}){speeches-> ///<SpeechesDb, LongesDb>
//                    speechSource.insert(speeches.item.map { it as SpeechTb })
//                        .listToResult({ LongesDb(it) })}
//                .flatMapCondition({ vl -> vl.item > 0L }) {  //<LongesDb, LongDb>
//                    copySets(exerciseTb.sets, ownerId = newId) }
//        } ?: ResultData.Error(ThrowableDS.NotValidType())