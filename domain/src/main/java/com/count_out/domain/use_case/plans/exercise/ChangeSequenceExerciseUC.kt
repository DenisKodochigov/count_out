package com.count_out.domain.use_case.plans.exercise

import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.ExerciseRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeSequenceExerciseUC @Inject constructor(
    configuration: Configuration, private val repo: ExerciseRepo
): UseCase<ChangeSequenceExerciseUC.Request, ChangeSequenceExerciseUC.Response>(configuration)  {

    override fun methodRepo(request: Request): Flow<ResultUC<TypeRepo>> =
        repo.changeSequenceExercise(TypeRepo.DataForChangeSequenceT(request.item))
    override fun response(typeRepo: TypeRepo): Response = Response(typeRepo)
    data class Request(val item: DataForChangeSequence): UseCase.Request
    data class Response(val training: TypeRepo): UseCase.Response
}
//    override fun implementation(request: Request): Flow<ResultUC<Response>> =
//        repo.changeSequenceExercise(request.item).map { ResultUC.Success(Response(it)) }