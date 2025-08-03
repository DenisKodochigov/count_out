package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.GlobalValueApp
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectTrainingUC @Inject constructor(
    configuration: Configuration, private val repoLastPlan: LastPlanRepo
): UseCase<SelectTrainingUC.Request, SelectTrainingUC.Response>(configuration)  {
    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> {
        GlobalValueApp.planRun.value = request.training
        return flow { emit(ResultUC.Success(
            TypeRepo.LongT(item = request.training.idTraining)))}
    }
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val training: Training): UseCase.Request
    data class Response(val selectedTraining: TypeRepo): UseCase.Response
}
//override fun implementation(request: Request): Flow<ResultUC<Response>> {
//        GlobalValueApp.planRun.value = request.training
//        val resultSave = repoLastPlan.saveLastUsedPlan(request.training.idTraining)
//        return flow { emit(ResultUC.Success(
//            Response(TypeRepo.LongMy(item = request.training.idTraining))))}
//    }