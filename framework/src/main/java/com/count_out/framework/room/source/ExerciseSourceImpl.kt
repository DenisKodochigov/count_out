package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.SetDb
import com.count_out.data.models.entity.SetViewIdDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTb.Companion.toTb
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.set.SetTb.Companion.toTb
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechSource: SpeechSourceImpl,
): ExerciseSource, PrimeSource() {

    override fun insert (exercise: Data): ResultData<Data> = runCatching {
        return if (exercise is ExerciseDb){
            copyWithDependencies(
                insertMain = { dao.insert(exercise.toTb(idExercise = 0L)) },
                getSpeeches = { idNew-> speechSource.getListSpeech(exerciseId = exercise.idExercise)
                    .map{ item-> item.apply{ exerciseId = idNew} }},
                insertSpeeches = { speechSource.insert(it) },
                copyNested = { id ->  copySets(exercise.sets,id)}
            )
        } else ResultData.Error(ThrowableDS.ErrorTypeExercise())
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    fun copySets(sets: List<SetDb>, ownerId: Long): ResultData<Data> =
        if (sets.isEmpty()) { setSource.insert( SetTb().copy(exerciseId = ownerId))}
        else sets.map { set-> setSource.insert( set.toTb(idSet = 0L, exerciseId = ownerId)) }[0]

    override fun update(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseDb, Long>{ item -> dao.update(item.toTb()).toLong() }

    override fun changeSequence(setViewId: Data): ResultData<Data> {
        return try {
            if (setViewId is SetViewIdDb) {
                val from = setViewId.from
                val to = setViewId.to
                val listExercise = dao.getExerciseRing(setViewId.idOwner).toMutableList()
                if (from > to) for ( id in to..< from){ listExercise[id].idView = id + 1 }
                else for ( id in (from + 1)..to){ listExercise[id].idView = id - 1}
                listExercise[from].idView = to
                dao.update(listExercise).let { result->
                    if (result == listExercise.count()) ResultData.Success(LongDb(result.toLong()))
                    else ResultData.Error(ThrowableDS.ErrorExercises())
                }
            } else ResultData.Error(ThrowableDS.ErrorTypeSetIdView())
        } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
    }

    override fun del(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseDb, Long>{ item -> dao.delete(item.toTb()).toLong() }
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