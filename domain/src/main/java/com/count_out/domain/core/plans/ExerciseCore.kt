package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseCore @Inject constructor(private val repo: ExerciseRepo): Core()  {
    fun del(exercise: Domain): Flow<ResultDomain<Domain>>{
        return repo.del(exercise)
    }
    fun copy(exercise: Domain): Flow<ResultDomain<Domain>>{
        return repo.copy(exercise)
    }
    fun update(exercise: Domain): Flow<ResultDomain<Domain>>{
        return repo.update(exercise)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun changeSequenceExercise(sequence: Domain): Flow<ResultDomain<Domain>>{
        return repo.changeSequence(sequence)
    }
}