package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>>
    fun changeSequenceExercise(setViewId: TypeRepo): Flow<ResultUC<TypeRepo>>
}
