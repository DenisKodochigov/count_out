package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun setViewId(setViewId: TypeRepo): Flow<ResultUC<TypeRepo>>
}
//    fun delRound(idRound: Long)
//    fun get(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
//    fun delRing(idRing: Long) roundId: Long = 0, ringId: Long = 0
//    fun getForRound(id: TypeRepo): Flow<ResultUC<TypeRepo>>
//    fun getForRing(id: TypeRepo): Flow<ResultUC<TypeRepo>>
//    fun getFilter(list: TypeRepo): Flow<ResultUC<TypeRepo>>