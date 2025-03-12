package com.count_out.domain.use_case.activity

//class SelectActivityUC @Inject constructor(
//    configuration: Configuration, private val repo: ExerciseRepo
//): UseCase<SelectActivityUC.Request, SelectActivityUC.Response>(configuration)  {
//    override fun executeData(input: Request): Flow<Response> =
//        repo.(input.activity).map { Response(it) }
//    data class Request(val activity: ActionWithActivity): UseCase.Request
//    data class Response(val exercise: Exercise): UseCase.Response
//}