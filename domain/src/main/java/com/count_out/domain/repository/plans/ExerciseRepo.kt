package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun get(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun changeSequenceExercise(item: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun getForRound(id: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun getForRing(id: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun getFilter(list: TypeRepo): Flow<ResultUC<TypeRepo>>
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long) roundId: Long = 0, ringId: Long = 0
}