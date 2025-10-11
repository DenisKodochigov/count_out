package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit

abstract class ExerciseDb: Data {
    abstract val idExercise: Long
    abstract val ringId: Long
    abstract val idView: Int
    abstract val activityId: Long
    abstract val activity: ActivityDb?
    abstract val speeches: List<SpeechDb>
    abstract val sets: List<SetDb>
    abstract val amountSet: Int
    abstract val duration: Double
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
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
}