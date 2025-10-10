package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.toData
import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepoImpl @Inject constructor(
    private val source: ExerciseSource): ExerciseRepo, PrimeRepo() {

    override fun del(exercise: Domain): Flow<ResultDomain<Domain>> {
        return source.del(toData(exercise)).convertor()
    }
    override fun copy(exercise: Domain): Flow<ResultDomain<Domain>> {
        return source.copy(toData(exercise)).convertor()
    }
    override fun update(exercise: Domain): Flow<ResultDomain<Domain>> {
        return source.update(toData(exercise)).convertor()
    }
    override fun changeSequenceExercise(setViewId: Domain): Flow<ResultDomain<Domain>> {
        return source.changeSequenceExercise(toData(setViewId)).convertor()
    }
}