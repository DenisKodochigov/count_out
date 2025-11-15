package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingCore @Inject constructor(private val repo: RingRepo): Core()  {
    fun del(ring: Domain): Flow<ResultDomain<Domain>>{
        return repo.del(ring)
    }
    fun copy(ring: Domain): Flow<ResultDomain<Domain>>{
        return repo.insert(ring)
    }
    fun update(ring: Domain): Flow<ResultDomain<Domain>>{
        return repo.update(ring)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun changeSequenceExercise(sequence: Domain): Flow<ResultDomain<Domain>>{
        return repo.changeSequenceExercise(sequence)
    }
}