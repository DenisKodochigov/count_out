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
        return wrapFlow(source.del(toTypeSource(exercise)))
    }
    override fun copy(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return wrapFlow(source.copy(toTypeSource(exercise)))
    }
    override fun update(exercise: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return wrapFlow(source.update(toTypeSource(exercise)))
    }
    override fun changeSequenceExercise(setViewId: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return wrapFlow( source.changeSequenceExercise(toTypeSource(setViewId)))
    }
}