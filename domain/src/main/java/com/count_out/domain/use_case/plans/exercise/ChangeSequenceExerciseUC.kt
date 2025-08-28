package com.count_out.domain.use_case.plans.exercise

import com.count_out.domain.core.plans.ExerciseCore
import com.count_out.domain.entity.SetViewId
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeSequenceExerciseUC @Inject constructor(
    configuration: Configuration, private val core: ExerciseCore
): UseCase<ChangeSequenceExerciseUC.Request, ChangeSequenceExerciseUC.Response>(configuration)  {

    override fun method(request: Request): Flow<ResultUC<TypeRepo>> =
        core.changeSequenceExercise(TypeRepo.SetViewIdT(request.item))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val item: SetViewId): UseCase.Request
    data class Response(val training: TypeRepo): UseCase.Response
}