package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit
interface ExerciseDb: Data {
    val idExercise: Long
    val ringId: Long
    val idView: Int
    val activityId: Long
    val activity: ActivityDb?
    val speeches: List<SpeechDb>
    val sets: List<SetDb>
    val amountSet: Int
    val duration: Double
    //    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Exercise {
        override val idExercise: Long = this@ExerciseDb.idExercise
        override val ringId: Long = this@ExerciseDb.ringId
        override val idView: Int = this@ExerciseDb.idView
        override val activity: Activity? = this@ExerciseDb.activity?.toDomain()
        override val activityId: Long = this@ExerciseDb.activityId
        override val speechKit: SpeechKit = SpeechKit.Companion.fill(this@ExerciseDb.speeches.map{it.toDomain()})
        override val sets: List<Set> = this@ExerciseDb.sets.map { it.toDomain() }
        override val amountSet: Int = this@ExerciseDb.amountSet
        override val duration: Parameter = Parameter.Companion.fill(this@ExerciseDb.duration, 2)
    }

    companion object {
        fun fromDomain(domain: Domain): ExerciseDb {
            return when (domain) {
                is Exercise -> object: ExerciseDb{
                    override val idExercise: Long = domain.idExercise
                    override val ringId: Long = domain.ringId
                    override val idView: Int = domain.idView
                    override val activityId: Long = domain.activityId
                    override val activity: ActivityDb? = domain.activity?.let { ActivityDb.fromDomain(it)}
                    override val speeches: List<SpeechDb> = domain.speechKit.toList().map { SpeechDb.fromDomain(it) }
                    override val sets: List<SetDb> = domain.sets.map { SetDb.fromDomain(it) }
                    override val amountSet: Int = domain.amountSet
                    override val duration: Double = domain.duration.value
                }
                else -> EMPTY
            }
        }
        val EMPTY = object: ExerciseDb{
            override val idExercise: Long = 0
            override val ringId: Long = 0
            override val idView: Int = 0
            override val activityId: Long = 0
            override val activity: ActivityDb? = null
            override val speeches: List<SpeechDb> = emptyList()
            override val sets: List<SetDb> = emptyList()
            override val amountSet: Int = 0
            override val duration: Double = 0.0
        }
    }
}
//abstract class ExerciseDb: Data {
//    abstract val idExercise: Long
//    abstract val ringId: Long
//    abstract val idView: Int
//    abstract val activityId: Long
//    abstract val activity: ActivityDb?
//    abstract val speeches: List<SpeechDb>
//    abstract val sets: List<SetDb>
//    abstract val amountSet: Int
//    abstract val duration: Double
////    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
//    override fun toDomain(ind: Int): Domain = object: Exercise {
//        override val idExercise: Long = this@ExerciseDb.idExercise
//        override val ringId: Long = this@ExerciseDb.ringId
//        override val idView: Int = this@ExerciseDb.idView
//        override val activity: Activity? = this@ExerciseDb.activity?.toDomain()
//        override val activityId: Long = this@ExerciseDb.activityId
//        override val speechKit: SpeechKit = SpeechKit.Companion.fill(this@ExerciseDb.speeches.map{it.toDomain()})
//        override val sets: List<Set> = this@ExerciseDb.sets.map { it.toDomain() }
//        override val amountSet: Int = this@ExerciseDb.amountSet
//        override val duration: Parameter = Parameter.Companion.fill(this@ExerciseDb.duration, 2)
//    }
//
//    companion object {
//        fun fromDomain(domain: Domain): ExerciseDb {
//            return when (domain) {
//                is Exercise -> object: ExerciseDb(){
//                    override val idExercise: Long = domain.idExercise
//                    override val ringId: Long = domain.ringId
//                    override val idView: Int = domain.idView
//                    override val activityId: Long = domain.activityId
//                    override val activity: ActivityDb? = domain.activity?.let { ActivityDb.fromDomain(it)}
//                    override val speeches: List<SpeechDb> = domain.speechKit.toList().map { SpeechDb.fromDomain(it) }
//                    override val sets: List<SetDb> = domain.sets.map { SetDb.fromDomain(it) }
//                    override val amountSet: Int = domain.amountSet
//                    override val duration: Double = domain.duration.value
//                }
//                else -> throw IllegalArgumentException("Unsupported domain type")
//            }
//        }
//    }
//}