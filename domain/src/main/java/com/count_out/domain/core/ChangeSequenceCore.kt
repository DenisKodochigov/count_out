package com.count_out.domain.core

import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.lg
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ChangeSequenceCore @Inject constructor(
    private val repoPlan: PlanRepo,
    private val repoRing: RingRepo,
    private val repoExer: ExerciseRepo): Core()  {
    fun set(request: Domain): Flow<ResultDomain<Domain>> {
        return if (request is SetViewId) {
            lg("ChangeSequenceCore id${request.idOwner} from${request.from} to${request.to}")
            when (request.owner) {
                is Plan -> { repoPlan.changeSequence(request) }
                is Ring -> { repoRing.changeSequence(request) }
                is Exercise -> { repoExer.changeSequence(request) }
                else-> flowOf(ResultDomain.Error(ThrowableUC.NotValidType()))
            }
        } else flowOf(ResultDomain.Error(ThrowableUC.NotValidType()))
    }
}