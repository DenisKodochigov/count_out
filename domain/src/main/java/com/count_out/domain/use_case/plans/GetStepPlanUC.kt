package com.count_out.domain.use_case.plans

import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.repository.LastPlanRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStepPlanUC @Inject constructor(configuration: Configuration, private val repo: ExecuteWorkOutRepo
): UseCase<GetStepPlanUC.Request, GetStepPlanUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        repo.getStepPlan().map {step->
            converter(step){ it1-> Response(it1)}}
    data object Request : UseCase.Request
    data class Response(val step: StepTraining) : UseCase.Response
}