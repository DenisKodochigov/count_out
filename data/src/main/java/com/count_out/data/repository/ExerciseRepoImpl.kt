package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.entity.SetViewIdDb
import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepoImpl @Inject constructor(
    private val source: ExerciseSource): ExerciseRepo {

    override fun del(exercise: Domain): Flow<ResultDomain<Domain>> {
        return source.del( ExerciseDb.fromDomain(exercise)).convertor()
    }
    override fun copy(exercise: Domain): Flow<ResultDomain<Domain>> {
        return source.insert(ExerciseDb.fromDomain(exercise)).convertor()
    }
    override fun update(exercise: Domain): Flow<ResultDomain<Domain>> {
        return source.update(ExerciseDb.fromDomain(exercise)).convertor()
    }
    override fun changeSequence(setViewId: Domain): Flow<ResultDomain<Domain>> {
        return source.changeSequence( SetViewIdDb.fromDomain(setViewId)).convertor()
    }
}