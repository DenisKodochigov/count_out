package com.count_out.domain.use_case.other

//import com.count_out.domain.entity.throwable.ResultDomain
//import com.count_out.domain.entity.workout.Domain
//import com.count_out.domain.use_case.UseCase
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject

//class ShowBottomSheetUC @Inject constructor(
//    private val configuration: Configuration, private val core: ShowBSCore
//): UseCase<ShowBottomSheetUC.Request, ShowBottomSheetUC.Response>(configuration)  {
//
//    override fun method(request: Request): Flow<ResultDomain<Domain>> = core.get(request.show)
//    override fun response(result: Domain): Response = Response(result)
//
//    data class Request(val show: Domain) : UseCase.Request
//    data class Response(val show: Domain) : UseCase.Response
//
//
//}
