package com.count_out.data.repository

import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepoImpl @Inject constructor(
    private val source: ExerciseSource): ExerciseRepo, PrimeRepo() {

    override fun del(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.del(toTypeSource(exercise)).wrapFlow()
    }
    override fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(exercise)).wrapFlow()
    }
    override fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.update(toTypeSource(exercise)).wrapFlow()
    }
    override fun changeSequenceExercise(setViewId: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.changeSequenceExercise(toTypeSource(setViewId)).wrapFlow()
    }
}