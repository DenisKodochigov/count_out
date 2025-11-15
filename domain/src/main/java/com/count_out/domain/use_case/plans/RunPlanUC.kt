package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.GlobalValueApp
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RunPlanUC @Inject constructor(
    configuration: Configuration, private val repoLastPlan: LastPlanRepo
): UseCase<RunPlanUC.Request, RunPlanUC.Response>(configuration)  {
    override fun method(request: Request): Flow<ResultDomain<Domain>> {
        GlobalValueApp.planLast.value = ResultDomain.Success(request.plan)
        return repoLastPlan.saveLastUsedPlan(LongDm(item = request.plan.idPlan))
              .wrap { LongDm(item = request.plan.idPlan) }
    }
    override fun response(result: Domain): Response = Response(result)
    data class Request(val plan: Plan): UseCase.Request
    data class Response(val selectedTraining: Domain): UseCase.Response
}
//override fun implementation(request: Request): Flow<ResultUC<Response>> {
//        GlobalValueApp.planRun.value = request.training
//        val resultSave = repoLastPlan.saveLastUsedPlan(request.training.idTraining)
//        return flow { emit(ResultUC.Success(
//            Response(Domain.LongMy(item = request.training.idTraining))))}
//    }