package com.example.domain.use_case

import com.example.domain.entity.Activity
import com.example.domain.entity.Training
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

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
class GetTrainingActivityUC  @Inject constructor(configuration: Configuration,
                                                 private val getTrainingUC: GetTrainingUC,
                                                 private val getActivitiesUC: GetActivitiesUC
): UseCase<GetTrainingActivityUC.Request, GetTrainingActivityUC.Response>(configuration)  {

    override fun executeData(input: Request): Flow<Response> {
        return combine(
            getTrainingUC.executeData(GetTrainingUC.Request(input.id)),
            getActivitiesUC.executeData(GetActivitiesUC.Request( input.id)))
        { training, activity -> Response(TrainingWithActivity(training.training, activity.activity)) }
    }
    data class Request(val id: Long) : UseCase.Request
    data class Response(val trainingWithActivity: TrainingWithActivity) : UseCase.Response
    data class TrainingWithActivity(val training: Training, val activities: List<Activity>)
}

