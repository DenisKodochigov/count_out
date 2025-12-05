package com.count_out.domain.use_case.workout_service

import com.count_out.domain.entity.ai.TrainingState
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.ExecuteWorkOutRepo
import com.count_out.domain.use_case.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class StartTrainingUC @Inject constructor(configuration: Configuration, private val repo: ExecuteWorkOutRepo
): UseCase<StartTrainingUC.Request, StartTrainingUC.Response>(configuration)  {
    private val state: MutableStateFlow<TrainingState> = MutableStateFlow(TrainingState.STOPPED)
    override fun method(request: Request): Flow<ResultDomain<Domain>> {
        return repo.start()
    }
    override fun response(result: Domain): Response = Response
    data object Request: UseCase.Request
    data object Response: UseCase.Response
}
//    override fun implementation_old(request: Request): Flow<Response> = repo.copy(request.training).map { Response(it) }
//    override fun implementation(request: Request): Flow<ResultUC<Response>> {
//        repo.start()
//        return flow { emit(ResultUC.Success(Response)) }
//    }