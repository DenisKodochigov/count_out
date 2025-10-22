package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Ring.Companion.amount
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RingOrExerciseCore @Inject constructor(private val repo: RingRepo): Core()  {
    fun setRingOrExercise(ring: Domain): Flow<ResultDomain<Domain>>{
        return (ring as? Ring)?.let {
            repo.insert (if (ring.amount > 1) ring.amount(1) else ring.amount(2)) } ?:
                flowOf(ResultDomain.Error(ThrowableUC.NotValidType()))
    }
}