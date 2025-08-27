package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.GlobalValueApp
import com.count_out.domain.entity.GlobalValueApp.toStepPlan1
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.TypeRepo.NullT.toStepPlan
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
* Надо передать план тренировки, которая может хнанится в:
 * 1. В GlobalValueApp
 * 2. Мы ее сохранили в предыдщей сессии работы (DataStore)
 * 3. Первый запуск и подставляем тренировку с индексом 1.
 */
class GetStepPlanUC @Inject constructor(
    configuration: Configuration,
    private val repoExecute: ExecuteWorkOutRepo,
    private val repoLastPlan: LastPlanRepo,
): UseCase<GetStepPlanUC.Request, GetStepPlanUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> {
        val result = GlobalValueApp.planRun.map { training ->
            training?.let {
                ResultUC.Success( TypeRepo.StepPlanT(item = toStepPlan1(it))) }
                ?: exceptionNull
        }

        return combine(result,
            repoLastPlan.getLastUsedPlan().wrap{ it.toStepPlan() },
            repoExecute.getPlan().wrap{ it.toStepPlan()}
        ){ r1, r2, r3 ->
            val result = r1.chek() ?: r2.chek() ?: r3.chek()
            when(result){
                is ResultUC.Error -> exceptionNull
                is ResultUC.Success -> ResultUC.Success(result.data)
                null -> exceptionNull
            }
        }
    }
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data object Request : UseCase.Request
    data class Response(val step: TypeRepo) : UseCase.Response
}
//            convertor4(repoLastPlan.getLastUsedPlan()){ tr->
//                TypeRepo.StepPlanMy(item = toStepPlan(tr.))},
//            convertor3(repoExecute.getPlan()){ tr-> toStepPlan(tr)})
//
//    override fun implementation(request: Request): Flow<ResultUC<Response>> {
//
//        val result = GlobalValueApp.planRun.map { training ->
//            training?.let {
//                ResultUC.Success( TypeRepo.StepPlanMy(item = toStepPlan1(it))) }
//                ?: exceptionNull
//        }
//
//        return combine(result,
//            repoLastPlan.getLastUsedPlan().wrap{ it.toStepPlan() },
//            repoExecute.getPlan1().wrap{ it.toStepPlan()}
//        ){ r1, r2, r3 ->
//            val result = r1.chek() ?: r2.chek() ?: r3.chek()
//            when(result){
//                is ResultUC.Error -> exceptionNull
//                is ResultUC.Success -> ResultUC.Success(Response(result.data))
//                null -> exceptionNull
//            }
//        }
//    }