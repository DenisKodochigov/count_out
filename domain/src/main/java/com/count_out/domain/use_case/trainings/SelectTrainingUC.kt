package com.count_out.domain.use_case.trainings

import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.other.CollapsingUC.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SelectTrainingUC @Inject constructor(configuration: Configuration
): UseCase<SelectTrainingUC.Request, SelectTrainingUC.Response>(configuration)  {
    override fun executeData(input: Request): Flow<Response> =
        flow { emit( Response(input.training.idTraining) ) }
    data class Request(val training: Training): UseCase.Request
    data class Response(val selectedTraining: Long): UseCase.Response
}