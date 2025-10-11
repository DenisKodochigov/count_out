package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit

abstract class SetDb: Data {
    abstract val idSet: Long
    abstract val name: String
    abstract val exerciseId: Long
    abstract val speeches: List<SpeechDb>
    abstract val goal: Int
    abstract val weightV: Double
    abstract val weightU: Int
    abstract val distanceV: Double
    abstract val distanceU: Int
    abstract val durationV: Double
    abstract val durationU: Int
    abstract val reps: Int
    abstract val intensity: Int
    abstract val intervalReps: Double
    abstract val intervalDown: Int
    abstract val groupCount: String
    abstract val timeRestV: Double
    abstract val timeRestU: Int
    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: Set{
        override val idSet: Long = this@SetDb.idSet
        override val name: String = this@SetDb.name
        override val exerciseId: Long = this@SetDb.exerciseId
        override val speechKit: SpeechKit = SpeechKit.fill(this@SetDb.speeches.map{it.toDomain()})
        override val goal: Goal = Goal.entries[this@SetDb.goal]
        override val weight: Parameter = Parameter.fill(this@SetDb.weightV,this@SetDb.weightU)
        override val distance: Parameter = Parameter.fill(this@SetDb.distanceV,this@SetDb.distanceU)
        override val duration: Parameter = Parameter.fill(this@SetDb.durationV,this@SetDb.durationU)
        override val reps: Int = this@SetDb.reps
        override val intensity: Zone = Zone.entries[ this@SetDb.intensity]
        override val intervalReps: Double = this@SetDb.intervalReps
        override val intervalDown: Int = this@SetDb.intervalDown
        override val groupCount: String = this@SetDb.groupCount
        override val rest: Parameter = Parameter.fill(this@SetDb.reps.toDouble(), 2)
    }
}