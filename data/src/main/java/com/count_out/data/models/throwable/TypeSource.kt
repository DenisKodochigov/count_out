package com.count_out.data.models.throwable

import com.count_out.data.models.ActivityDb
import com.count_out.data.models.ExerciseDb
import com.count_out.data.models.NameIdDb
import com.count_out.data.models.PartDb
import com.count_out.data.models.PlanDb
import com.count_out.data.models.RingDb
import com.count_out.data.models.SetDb
import com.count_out.data.models.SettingsDb
import com.count_out.data.models.SpeechDb
import com.count_out.data.models.WeatherDb
import com.count_out.data.models.old.SetViewIdD
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit
import kotlin.collections.get

sealed class TypeSource {
    data class IntT(val item: Int) : TypeSource()
    data class LongT(val item: Long) : TypeSource()
    data class StringT(val item: String) : TypeSource()
    data class BooleanT(val item: Boolean) : TypeSource()
    data class SpeechT(val item: SpeechDb) : TypeSource()
    data class SetT(val item: SetDb) : TypeSource()
    data class ActivityT(val item: ActivityDb): TypeSource()
    data class ActivitiesT(val item: List<ActivityDb>) : TypeSource()
    data class ExerciseT(val item: ExerciseDb) : TypeSource()
    data class ExercisesT(val item: List<ExerciseDb>) : TypeSource()
    data class RingT(val item: RingDb) : TypeSource()
    data class RingsT(val item: List<RingDb>) : TypeSource()
    data class PartT(val item: PartDb) : TypeSource()
    data class PartsT(val item: List<PartDb>) : TypeSource()
    data class PlanT(val item: PlanDb) : TypeSource()
    data class PlansT(val item: List<PlanDb>) : TypeSource()
    data class SetViewIdT(val item: SetViewIdD): TypeSource()
    data class SettingsT(val item: SettingsDb) : TypeSource()
    data class DeviceUIT(val item: DeviceBle) : TypeSource()
    data class DevicesUIT(val item: Map<String, DeviceBle>) : TypeSource()
    data class BleConnectStateT(val item: ConnectState) : TypeSource()
    data class WeatherT(val item: WeatherDb) : TypeSource()
    data class WeatherRequestT(val item: WeatherRequest) : TypeSource()
    data class NameIdT(val item : NameIdDb): TypeSource()
    data object NullT : TypeSource()


    companion object {
        inline fun TypeSource.useLong(crossinline block: (Long) -> Long): ResultSource<TypeSource> =
            if (this is TypeSource.LongT) {
                try { block(this.item).result() }
                catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
            } else ResultSource.Error(ThrowableDS.NotValidType())

        fun Long.result(): ResultSource<TypeSource.LongT> =
            if (this > 0L) ResultSource.Success(TypeSource.LongT(this))
            else ResultSource.Error(ThrowableDS.RequestFailed())

        fun TypeSource.toRepo(): TypeRepo =
            when (this) {
                is IntT -> TypeRepo.IntT(item = this.item)
                is LongT -> TypeRepo.LongT(item = this.item)
                is StringT -> TypeRepo.StringT(item = this.item)
                is BooleanT -> TypeRepo.BooleanT(item = this.item)
                is SpeechT -> TypeRepo.SpeechT(item = convSpeech(this.item))
                is ActivitiesT -> TypeRepo.ActivitiesT(item = convListActivity(this.item))
                is ActivityT -> TypeRepo.ActivityT(item = convActivity(this.item))
                is PlanT -> TypeRepo.PlanT(item = convPlan(this.item))
                is PlansT -> TypeRepo.PlansT(item = convListPlan(this.item))
                is SettingsT -> TypeRepo.SettingsT(item = convSetting(this.item))
                is DeviceUIT -> TypeRepo.DeviceUIT(item = this.item)
                is DevicesUIT -> TypeRepo.DevicesUIT(item = this.item)
                is WeatherT -> TypeRepo.WeatherT(item = convWeather(this.item))
                is WeatherRequestT -> TypeRepo.WeatherRequestT(item = this.item)
                is BleConnectStateT -> TypeRepo.BleConnectStateT(item = this.item)
                else -> TypeRepo.NullT
            }
        fun convWeather(item: WeatherDb) = object : Weather {
            override val time: Long = item.time
            override val interval: Int = item.interval
            override val temperature2m: Double = item.temperature2m
            override val relativeHumidity2m: Int = item.relativeHumidity2m
            override val apparentTemperature: Double = item.apparentTemperature
            override val isDay: Int = item.isDay
            override val precipitation: Double = item.precipitation
            override val rain: Double = item.rain
            override val showers: Double = item.showers
            override val snowfall: Double = item.snowfall
            override val weatherCode: Int = item.weatherCode
            override val cloudCover: Int = item.cloudCover
            override val pressureMsl: Double = item.pressureMsl
            override val surfacePressure: Double = item.surfacePressure
            override val windSpeed10m: Double = item.windSpeed10m
            override val windDirection10m: Int = item.windDirection10m
            override val windGusts10m: Double = item.windGusts10m
        }
        fun convSetting(item: SettingsDb) = when(item){
            is SettingsDb.AddressBle -> Settings.AddressBle(item.item)
            is SettingsDb.NameBle -> Settings.NameBle(item.item)
            is SettingsDb.SpeechDescription -> Settings.SpeechDescription(item.item)
        }
        fun convSpeech(speech: SpeechDb) = object : Speech {
            override val idSpeech = speech.idSpeech
            override val idKit: Long = speech.idKit
            override val message = speech.message
            override val duration = speech.duration
            override val addMessage = speech.addMessage
        }
        fun convListSpeech(speeches: List<SpeechDb>): SpeechKit {
            val convSpeeches = if (speeches.size > 3) {
                listOf(speeches[0], speeches[1], speeches[2], speeches[3]).map(::convSpeech)
            } else { List(4) { Speech.EMPTY } }

            return object : SpeechKit {
                override val beforeStart: Speech = convSpeeches[0]
                override val afterStart: Speech = convSpeeches[1]
                override val beforeEnd: Speech = convSpeeches[2]
                override val afterEnd: Speech = convSpeeches[3]
            }
        }
        fun convListActivity(activities: List<ActivityDb>) =
            activities.map { activity -> convActivity(activity) }
        fun convActivity(activity: ActivityDb) = object : Activity {
            override val idActivity: Long = activity.idActivity
            override val name: String = activity.name
            override val description: String = activity.description
            override val icon: Int = activity.icon
            override val color: Int = activity.color
            override val videoClip: String = activity.videoClip
            override val audioTrack: String = activity.audioTrack
        }
        fun convListPlan(plans: List<PlanDb>) = plans.map { plan -> convPlan(plan) }
        fun convPlan(plan: PlanDb) = object : Plan {
            override val idPlan: Long = plan.idPlan
            override val name: String = plan.name
            override val amountActivity: Int = plan.amountActivity
            override val parts: List<Part> = convListPart(plan.parts)
            override val speechKit: SpeechKit = convListSpeech(plan.speeches)
        }
        fun convListPart(parts: List<PartDb>): List<Part>{
            var i = 0
            return parts.map { part -> convPart(part, i++) }
        }
        fun convPart(plan: PartDb, name: Int) = object : Part {
            override val idPart: Long = plan.idPart
            override val planId: Long = plan.planId
            override val name: PartName = PartName.entries[name]
            override val rings: List<Ring> = convListRing(plan.rings)
            override val amount: Int = plan.amount
            override val speechKit: SpeechKit = convListSpeech(plan.speeches)
            override val duration: Parameter = object: Parameter{
                override val value: Double = plan.duration
                override val unit: Units = Units.M }
        }
        fun convListRing(rings: List<RingDb>): List<Ring> = rings.map { ring -> convRing(ring) }
        fun convRing(ring: RingDb) = object: Ring {
            override val idRing: Long = ring.idRing
            override val partId: Long = ring.partId
            override val numberLaps: Int = ring.numberLaps
            override val amount: Int = ring.amount
            override val speechKit: SpeechKit = convListSpeech(ring.speeches)
            override val exercises: List<Exercise> = convListExercise(ring.exercises)
            override val duration: Parameter = object: Parameter{
                override val value: Double = ring.duration
                override val unit: Units = Units.M }
        }
        fun convListExercise(exercises: List<ExerciseDb>): List<Exercise> =
            exercises.map { exercise -> convExercise(exercise) }
        fun convExercise(exercise: ExerciseDb) = object: Exercise {
            override val idExercise: Long = exercise.idExercise
            override val ringId: Long = exercise.ringId
            override val idView: Int = exercise.idView
            override val activity: Activity? = exercise.activity?.let {convActivity(it)}
            override val activityId: Long = exercise.activityId
            override val speechKit: SpeechKit = convListSpeech(exercise.speeches)
            override val sets: List<Set> = convListSet(exercise.sets)
            override val amountSet: Int = exercise.amountSet
            override val duration: Parameter = object: Parameter{
                override val value: Double = exercise.duration
                override val unit: Units = Units.M }
        }
        fun convListSet(sets: List<SetDb>): List<Set> = sets.map { set -> convSet(set) }
        fun convSet(set: SetDb) = object: Set {
            override val idSet: Long = set.idSet
            override val name: String = set.name
            override val exerciseId: Long = set.exerciseId
            override val speechKit: SpeechKit = convListSpeech(set.speeches)
            override val goal: Goal = Goal.entries[set.goal]
            override val reps: Int = set.reps
            override val intensity: Zone = Zone.entries[set.intensity]
            override val intervalReps: Double = set.intervalReps
            override val intervalDown: Int = set.intervalDown
            override val groupCount: String = set.groupCount
            override val weight: Parameter = object: Parameter{
                override val value: Double = set.weightV
                override val unit: Units = Units.entries[set.weightU]}
            override val distance: Parameter = object: Parameter{
                override val value: Double = set.distanceV
                override val unit: Units = Units.entries[set.distanceU]}
            override val duration: Parameter = object: Parameter{
                override val value: Double = set.durationV
                override val unit: Units = Units.entries[set.durationU]}
            override val rest: Parameter = object: Parameter{
                override val value: Double = set.durationV
                override val unit: Units = Units.entries[set.durationU]}

        }
    }
}


//        is LongsT -> TypeRepo.LongsT(item = this.item)
//        is SpeechesT -> TypeRepo.SpeechesT(item = convListSpeech(this.item))
//        is SetT -> TypeRepo.SetT(item = this.item as Set)
//        is SetsT -> TypeRepo.SetsT(item = this.item as List<Set>)
//        is ExerciseT -> TypeRepo.ExerciseT(item = this.item as Exercise)
//        is ExercisesT -> TypeRepo.ExercisesT(item = this.item as List<Exercise>)
//        is RingT -> TypeRepo.RingT(item = this.item as Ring)
//        is RingsT -> TypeRepo.RingsT(item = this.item as List<Ring>)
//        is PartT -> TypeRepo.PartT(item = this.item as Part)
//        is PartsT -> TypeRepo.PartsT(item = this.item as List<Part>)


//data class LongT(override val item: Long): TypeSource(), HasItem<Long>
//data class LongsT(override val item: List<Long>): TypeSource(), HasItem<List<Long>>
//data class StringT(override val item: String): TypeSource(), HasItem<String>
//data class BooleanT(override val item: Boolean): TypeSource(), HasItem<Boolean>
//data class CollapsingT(override val item: Collapsing): TypeSource(), HasItem<Collapsing>
//data class ShowBottomSheetT(override val item: ShowBottomSheet): TypeSource(), HasItem<ShowBottomSheet>
//data class SpeechT(override val item: SpeechDb): TypeSource(), HasItem<SpeechDb>
//data class SpeechesT(override val item: List<SpeechDb>): TypeSource(), HasItem<List<SpeechDb>>
//data class SpeechKitT(override val item: SpeechKitDb): TypeSource(), HasItem<SpeechKitDb>
//data class SetT(override val item: SetDb): TypeSource(), HasItem<SetDb>
//data class SetsT(override val item: List<SetDb>): TypeSource(), HasItem<List<SetDb>>
//data class ActivityT(override val item: ActivityDb): TypeSource(), HasItem<ActivityDb>
//data class ActivitiesT(override val item: List<ActivityDb>): TypeSource(), HasItem<List<ActivityDb>>
//data class ExerciseT(override val item: ExerciseDb): TypeSource(), HasItem<ExerciseDb>
//data class ExercisesT(override val item: List<ExerciseDb>): TypeSource(), HasItem<List<ExerciseDb>>
//data class SetViewIdT(override val item: SetViewIdD): TypeSource(), HasItem<SetViewIdD>
//data class RingT(override val item: RingDb): TypeSource(), HasItem<RingDb>
//data class RingsT(override val item: List<RingDb>): TypeSource(), HasItem<List<RingDb>>
//data class PartT(override val item: PartDb): TypeSource(), HasItem<PartDb>
//data class PartsT(override val item: List<PartDb>): TypeSource(), HasItem<List<PartDb>>
//data class PlanT(override val item: PlanDb): TypeSource(), HasItem<PlanDb>
//data class PlansT(override val item: List<PlanDb>): TypeSource(), HasItem<List<PlanDb>>
//data class StepPlanT(override val item: StepPlan): TypeSource(), HasItem<StepPlan>
//data class SettingT(override val item: Setting): TypeSource(), HasItem<Setting>
//data class SettingsT(override val item: Settings): TypeSource(), HasItem<Settings>
//data class DeviceUIT(override val item: DeviceBle): TypeSource(), HasItem<DeviceBle>
//data class DevicesUIT(override val item: Map<String, DeviceBle>): TypeSource(), HasItem<Map<String, DeviceBle>>
//data class BleConnectStateT(override val item: ConnectState): TypeSource(), HasItem<ConnectState>
//data class WeatherT(override val item: Weather): TypeSource(), HasItem<Weather>
//data class WeatherRequestT(override val item: WeatherRequest): TypeSource(), HasItem<WeatherRequest>
//data object NullT: TypeSource()