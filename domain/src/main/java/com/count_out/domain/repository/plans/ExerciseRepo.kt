package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun del(exercise: Domain): Flow<ResultDomain<Domain>>
    fun copy(exercise: Domain): Flow<ResultDomain<Domain>>
    fun update(exercise: Domain): Flow<ResultDomain<Domain>>
    fun changeSequenceExercise(setViewId: Domain): Flow<ResultDomain<Domain>>
}
