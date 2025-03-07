package com.count_out.domain.use_case.other

//class GetTrainingActivityUC(dispatcher: CoroutineDispatcher,
//                            private val trainingRepo: TrainingRepo,
//                            private val activityRepo: ActivityRepo
//): UseCase<GetTrainingActivityUC.Request, GetTrainingActivityUC.Response>(dispatcher)  {
//
//    fun getTrainingActivity(id: Long) = combine(
//            trainingRepo.getTraining(id), activityRepo.getActivities(id)
//        ){ training, activity ->
//            Result.Success(TrainingWithActivity(training, activity)) as Result<TrainingWithActivity>
//        }.flowOn(Dispatchers.IO).catch { emit( Result.Error(ThrowableUC.extractThrowable(it))) }
//
//    override fun executeData(input: Request): Flow<Response> {
//        return combine(trainingRepo.getTraining(input.id), activityRepo.getActivities(input.id))
//            { training, activity -> Response(TrainingWithActivity(training, activity)) }
//        }
//    data class Request(val id: Long) : UseCase.Request
//    data class Response(val trainingWithActivity: TrainingWithActivity) : UseCase.Response
//}
//class GetTrainingActivityUC  @Inject constructor(
//    configuration: Configuration,
//    private val getTrainingUC: GetTrainingUC,
//    private val getActivitiesUC: GetsActivityUC
//): UseCase<GetTrainingActivityUC.Request, GetTrainingActivityUC.Response>(configuration)  {

//    override fun executeData(input: Request): Flow<Response> {
//        return combine(
//            getTrainingUC.executeData(GetTrainingUC.Request(input.id)),
//            getActivitiesUC.executeData(GetsActivityUC.Request( input.id)))
//        { training, activity -> Response(TrainingWithActivity(training.training, activity.activity)) }
//    }
//    data class Request(val id: Long) : UseCase.Request
//    data class Response(val trainingWithActivity: TrainingWithActivity) : UseCase.Response
//    data class TrainingWithActivity(val training: Training, val activities: List<Activity>)
//}

