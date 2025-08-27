package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExerciseCore @Inject constructor(private val repo: ExerciseRepo): Core()  {
    fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.del(exercise)
    }
    fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.copy(exercise)
    }
    fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.update(exercise)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun changeSequenceExercise(sequence: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return if (sequence is TypeRepo.DataForChangeSequenceT){
            val roundId = sequence.item.roundId
            val from = sequence.item.from.toLong()
            val to = sequence.item.to.toLong()
            val step1 = SetViewId(roundId = roundId, viewId = to, newViewId = -1L)
            repo.setViewId(TypeRepo.SetViewIdT(step1)).flatMapConcat { res1 ->
                if (res1 is ResultUC.Error) flow { emit(res1) }
                else {
                    val step2 = SetViewId(roundId = roundId, viewId = from, newViewId = to)
                    repo.setViewId(TypeRepo.SetViewIdT(step2)).flatMapConcat { res2 ->
                        if (res2 is ResultUC.Error) flow { emit(res2) }
                        else {
                            val step3 = SetViewId(roundId = roundId, viewId = -1L, newViewId = from)
                            repo.setViewId(TypeRepo.SetViewIdT(step3))
                        }
                    }
                }
            }
        } else flow { emit(ResultUC.Error(ThrowableUC.NotValidType())) }
    }
//    fun get(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return repo.get(exercise)
//    }
//    fun getForRound(id: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return repo.getForRound(id)
//    }
//    fun getForRing(id: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return repo.getForRing(id)
//    }
//    fun getFilter(list: TypeRepo): Flow<ResultUC<TypeRepo>>{
//        return repo.getFilter(list)
//    }
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long) roundId: Long = 0, ringId: Long = 0
}