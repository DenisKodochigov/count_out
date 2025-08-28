package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
        return repo.changeSequenceExercise(sequence)
    }
}