package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Set.Companion.copy
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetCore @Inject constructor(private val repo: SetRepo): Core()  {
    fun copy(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.copy(set) }
    fun del(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.del(set) }
    fun update(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.update(set) }
    fun changeGoal(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.update(
            if (set is Set){ set.copy(goal = Goal.entries[(set.goal.ordinal + 1)%3]) } else set) }
    fun changeZone(set: Domain): Flow<ResultDomain<Domain>> {
        return repo.update(
            if (set is Set) set.copy(intensity = Zone.entries[(set.intensity.ordinal + 1)%5] )
            else set) }
}