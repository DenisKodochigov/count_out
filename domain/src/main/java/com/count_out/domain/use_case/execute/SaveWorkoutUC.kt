package com.count_out.domain.use_case.execute

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveWorkoutUC @Inject constructor(configuration: Configuration, private val repo: ExecuteWorkOutRepo
): UseCase<SaveWorkoutUC.Request, SaveWorkoutUC.Response>(configuration)  {

    override fun implementation(request: Request): Flow<ResultUC<Response>> {
        repo.save()
        return flow { emit(ResultUC.Success(Response)) }
    }

    data object Request: UseCase.Request
    data object Response: UseCase.Response
}