package com.count_out.data.repository

import android.util.Log
import com.count_out.data.models.ActivityDb
import com.count_out.data.models.ExerciseDb
import com.count_out.data.models.NameIdDb
import com.count_out.data.models.PartDb
import com.count_out.data.models.PlanDb
import com.count_out.data.models.RingDb
import com.count_out.data.models.SetDb
import com.count_out.data.models.SettingsDb
import com.count_out.data.models.SpeechDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.models.throwable.TypeSource.Companion.toRepo
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.TypeRepo.ActivityT
import com.count_out.domain.entity.TypeRepo.ExerciseT
import com.count_out.domain.entity.TypeRepo.LongT
import com.count_out.domain.entity.TypeRepo.PlanT
import com.count_out.domain.entity.TypeRepo.SetT
import com.count_out.domain.entity.TypeRepo.SettingsT
import com.count_out.domain.entity.TypeRepo.StringT
import com.count_out.domain.entity.TypeRepo.WeatherRequestT
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class PrimeRepo {
    val throwableNull = ResultUC.Error(ThrowableUC.extract(Exception("return null")))

    fun Flow<ResultSource<TypeSource>?>.convertor(): Flow<ResultUC<TypeRepo>> {
        return this.filterNotNull().map{ resultS->
            when(resultS){
                is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
                is Success -> ResultUC.Success(resultS.data.toRepo())
            }
        }
//        .flowOn(Dispatchers.IO)
//        .catch { emit(ResultUC.Error(ThrowableUC.extract(it)))}
    }
//        var lastValue:TypeSource = TypeSource.NullT
//        return this.filterNotNull().filter { it != lastValue }.map{ resultS->
//                when(resultS){
//                    is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
//                    is Success -> {
//                        lastValue = resultS.data
//                        ResultUC.Success(resultS.data.toRepo())
//                    }
//                }
//            }
//            .flowOn(Dispatchers.IO)
//            .catch { emit(ResultUC.Error(ThrowableUC.extract(it)
//            ) as ResultUC<Nothing>) }

    fun ResultSource<TypeSource>.wrapFlow(): Flow<ResultUC<TypeRepo>> {
        return flowOf(
            when (this) {
                is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(this.throwable))
                is Success -> ResultUC.Success(this.data.toRepo())
            }
        ) }
    fun toTypeSource(value: TypeRepo): TypeSource{
        return when(value){
            is SettingsT -> TypeSource.SettingsT(item = settingsDb(value.item))
            is StringT -> TypeSource.StringT(item = value.item)
            is ActivityT -> TypeSource.ActivityT(item = activityDb(value.item))
            is ExerciseT -> TypeSource.ExerciseT(item = exerciseDb(value.item))
            is LongT -> TypeSource.LongT(item = value.item)
            is SetT -> TypeSource.SetT(item = setDb( value.item))
            is TypeRepo.SpeechT -> TypeSource.SpeechT(speechDb(value.item))
            is PlanT -> TypeSource.PlanT(item = planDb(value.item))
            is WeatherRequestT-> TypeSource.WeatherRequestT(item = value.item)
            is NameId-> TypeSource.NameIdT(item =  NameIdDb(name = value.name, id = value.id ))
            else -> TypeSource.NullT
        }
    }
    fun settingsDb(item: Settings) = when(item){
        is Settings.AddressBle -> SettingsDb.AddressBle(item.item)
        is Settings.NameBle -> SettingsDb.NameBle(item.item)
        is Settings.SpeechDescription -> SettingsDb.SpeechDescription(item.item)
    }
    fun planDb(item: Plan) = object: PlanDb {
        override val idPlan: Long = item.idPlan
        override val name: String = item.name
        override val speeches: List<SpeechDb> = listSpeech(item.speechKit)
        override val parts: List<PartDb> = item.parts.map { partDb(it) }
        override val amountActivity: Int = item.amountActivity
    }
    fun partDb(item: Part) = object: PartDb{
        override val idPart: Long = item.idPart
        override val planId: Long = item.planId
        override val speeches: List<SpeechDb> = listSpeech(item.speechKit)
        override val rings: List<RingDb> = item.rings.map { ringDb(it) }
        override val amount: Int = item.amount
        override val duration: Double = item.duration.value
    }
    fun ringDb(item: Ring) = object : RingDb{
        override val idRing: Long = item.idRing
        override val partId: Long = item.partId
        override val speeches: List<SpeechDb> = listSpeech(item.speechKit)
        override val exercises: List<ExerciseDb> = item.exercises.map { exerciseDb(it) }
        override val amount: Int = item.amount
        override val duration: Double = item.duration.value
        override val numberLaps: Int = item.numberLaps
    }
    fun exerciseDb(item: Exercise) = object : ExerciseDb{
        override val idExercise: Long = item.idExercise
        override val ringId: Long = item.ringId
        override val idView: Int = item.idView
        override val activityId: Long = item.activityId
        override val activity: ActivityDb? = item.activity?.let { activityDb(it) }
        override val speeches: List<SpeechDb> = listSpeech(item.speechKit)
        override val sets: List<SetDb> = item.sets.map { setDb(it) }
        override val amountSet: Int = item.amountSet
        override val duration: Double = item.duration.value
    }
    fun activityDb(item: Activity) = object: ActivityDb{
        override val idActivity: Long = item.idActivity
        override val name: String = item.name
        override val description: String = item.description
        override val icon: Int = item.icon
        override val color: Int = item.color
        override val videoClip: String = item.videoClip
        override val audioTrack: String = item.audioTrack }
    fun speechDb(item: Speech) = object: SpeechDb{
        override val idSpeech: Long = item.idSpeech
        override val setId: Long? = item.setId
        override val exerciseId: Long? = item.exerciseId
        override val ringId: Long? = item.ringId
        override val partId: Long? = item.partId
        override val planId: Long? = item.planId
        override val message: String = item.message
        override val duration: Long = item.duration
        override val addMessage: String = item.addMessage
    }
    fun setDb(item: Set) = object: SetDb{
        override val idSet: Long = item.idSet
        override val name: String = item.name
        override val exerciseId: Long = item.exerciseId
        override val speeches: List<SpeechDb> = listSpeech(item.speechKit)
        override val goal: Int = item.goal.ordinal
        override val weightV: Double = item.weight.value
        override val weightU: Int = item.weight.unit.ordinal
        override val distanceV: Double = item.distance.value
        override val distanceU: Int = item.distance.unit.ordinal
        override val durationV: Double = item.duration.value
        override val durationU: Int = item.duration.unit.ordinal
        override val reps: Int = item.reps
        override val intensity: Int = item.intensity.ordinal
        override val intervalReps: Double = item.intervalReps
        override val intervalDown: Int = item.intervalDown
        override val groupCount: String = item.groupCount
        override val timeRestV: Double = item.rest.value
        override val timeRestU: Int = item.rest.unit.ordinal
    }
    fun listSpeech(kit: SpeechKit): List<SpeechDb>{
        return listOf(speechDb(kit.beforeStart),
            speechDb(kit.afterStart),
            speechDb(kit.beforeEnd),
            speechDb(kit.afterEnd),)
    }
}




//            is IntT -> TypeSource.IntT(item = value.item)
////            is LongsT-> TypeSource.LongsT(item = value.item)
//            is BooleanT -> TypeSource.BooleanT(item = value.item)
//            is SetsT -> TypeSource.SetsT(item = value.item)
//            is ActivitiesT -> TypeSource.ActivitiesT(item = value.item)
//            is ExercisesT -> TypeSource.ExercisesT(item = value.item)
//            is PlansT -> TypeSource.PlansT(item = value.item)
////            is StepPlanT -> TypeSource.StepPlanT(item = value.item)
//            is SettingsT -> TypeSource.SettingsT(item = value.item)
//            is DeviceUIT-> TypeSource.DeviceUIT(item = value.item)
//            is DevicesUIT -> TypeSource.DevicesUIT(item = value.item)
//            is WeatherT -> TypeSource.WeatherT(item = value.item)
//            is NullT -> TypeSource.NullT
//            is BleConnectStateT -> TypeSource.BleConnectStateT(item = value.item)
//            is TypeRepo.SetViewIdT -> TypeSource.SetViewIdT(item = SetViewIdD(value.item))

//    fun ResultSource<TypeSource>.convertor(): ResultUC<TypeRepo> {
//        return when (this) {
//            is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(this.throwable))
//            is Success -> ResultUC.Success(this.data.toRepo())
//        }
//    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    fun Flow<ResultSource<TypeSource>>.concatOk(action: ()-> Flow<ResultSource<TypeSource>>
//    ): Flow<ResultUC<TypeRepo>> {
//        return this.flatMapConcat {
//            when (it) {
//                is ResultSource.Error -> flowOf(
//                    ResultUC.Error(ThrowableUC.extract(it.throwable)))
//                is Success -> {
//                    if(getResult(it.data) > 0L ){
//                        action().map {resultSource ->
//                            when(resultSource){
//                                is Success-> { ResultUC.Success( resultSource.data.toRepo())}
//                                is ResultSource.Error -> { ResultUC.Error(ThrowableUC.extract(resultSource.throwable))}
//                            }
//                        }
//                    } else { flowOf(throwableNull)}
//                }
//            }
//        }
//    }
//    fun getResult(resultSource: TypeSource): Int{
//        return when(resultSource){
//            is TypeSource.IntT -> resultSource.item
//            is TypeSource.LongT -> resultSource.item.toInt()
//            is TypeSource.BooleanT -> if (resultSource.item) 1 else 0
//            else -> 0
//        }
//    }