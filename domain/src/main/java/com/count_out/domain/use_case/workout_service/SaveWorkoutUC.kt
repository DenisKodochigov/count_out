package com.count_out.domain.use_case.workout_service

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.types_domai.BooleanDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveWorkoutUC @Inject constructor(configuration: Configuration, private val repo: ExecuteWorkOutRepo
): UseCase<SaveWorkoutUC.Request, SaveWorkoutUC.Response>(configuration)  {


    override fun method(request: Request): Flow<ResultDomain<Domain>>{
        repo.save()
        return flow { emit(ResultDomain.Success(BooleanDm(item = true))) }
    }
    override fun response(result: Domain): Response = Response
    data object Request: UseCase.Request
    data object Response: UseCase.Response
}
//     fun implementation(request: Request): Flow<ResultUC<Response>> {
//        repo.save()
//        return flow { emit(ResultUC.Success(Response)) }
//    }