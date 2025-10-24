package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.types_domai.BooleanDm
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SelectedExerciseCore @Inject constructor(): Core()  {
    fun setRingOrExercise(list: Domain): Flow<ResultDomain<Domain>>{
        return flowOf(ResultDomain.Success(BooleanDm(true)))}
}