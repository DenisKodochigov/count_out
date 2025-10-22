package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Zone
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit

interface SetDb: Data {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speeches: List<SpeechDb>
    val goal: Int
    val weightV: Double
    val weightU: Int
    val distanceV: Double
    val distanceU: Int
    val durationV: Double
    val durationU: Int
    val reps: Int
    val intensity: Int
    val intervalReps: Double
    val intervalDown: Int
    val groupCount: String
    val timeRestV: Double
    val timeRestU: Int
//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
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
    companion object{
        fun fromDomain(domain: Domain): SetDb {
            return when (domain) {
                is Set -> object: SetDb{
                    override val idSet: Long = domain.idSet
                    override val name: String = domain.name
                    override val exerciseId: Long = domain.exerciseId
                    override val speeches: List<SpeechDb> =
                        domain.speechKit.toList().map { SpeechDb.fromDomain(it) }
                    override val goal: Int = domain.goal.ordinal
                    override val weightV: Double = domain.weight.value
                    override val weightU: Int = domain.weight.unit.ordinal
                    override val distanceV: Double = domain.distance.value
                    override val distanceU: Int = domain.distance.unit.ordinal
                    override val durationV: Double = domain.duration.value
                    override val durationU: Int = domain.duration.unit.ordinal
                    override val reps: Int = domain.reps
                    override val intensity: Int = domain.intensity.ordinal
                    override val intervalReps: Double = domain.intervalReps
                    override val intervalDown: Int = domain.intervalDown
                    override val groupCount: String = domain.groupCount
                    override val timeRestV: Double = domain.rest.value
                    override val timeRestU: Int = domain.rest.unit.ordinal
                }
                else -> EMPTY
            }
        }
        val EMPTY = object: SetDb{
            override val idSet: Long = 0
            override val name: String = ""
            override val exerciseId: Long = 0
            override val speeches: List<SpeechDb> = emptyList()
            override val goal: Int = 0
            override val weightV: Double = 0.0
            override val weightU: Int = 0
            override val distanceV: Double = 0.0
            override val distanceU: Int = 0
            override val durationV: Double = 0.0
            override val durationU: Int = 0
            override val reps: Int = 0
            override val intensity: Int = 0
            override val intervalReps: Double = 0.0
            override val intervalDown: Int = 0
            override val groupCount: String = ""
            override val timeRestV: Double = 0.0
            override val timeRestU: Int = 0
        }
    }
}