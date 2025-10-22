package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface PlanRepo {
    fun get(idPlan: Domain): Flow<ResultDomain<Domain>> //: Flow<Training>
    fun gets(): Flow<ResultDomain<Domain>> //: Flow<List<Training>>
    fun del(plan: Domain): Flow<ResultDomain<Domain>> //: Flow<List<Training>>
    fun copy(plan: Domain): Flow<ResultDomain<Domain>> //: Flow<List<Training>>
//    fun select(training: Domain): Flow<ResultUC<Domain>> //: Flow<List<Training>>
    fun update(nameId: Domain): Flow<ResultDomain<Domain>> // Flow<Training>
}