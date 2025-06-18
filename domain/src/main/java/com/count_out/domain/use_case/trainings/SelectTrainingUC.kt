package com.count_out.domain.use_case.trainings

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SelectTrainingUC @Inject constructor(configuration: Configuration
): UseCase<SelectTrainingUC.Request, SelectTrainingUC.Response>(configuration)  {
//    override fun implementation_old(request: Request): Flow<Response> =
//        flow { emit( Response(request.training.idTraining) ) }
    override fun implementation(request: Request): Flow<ResultUC<Response>> =
        flow { emit(  ResultUC.Success(Response(request.training.idTraining))) }
    data class Request(val training: Training): UseCase.Request
    data class Response(val selectedTraining: Long): UseCase.Response
}