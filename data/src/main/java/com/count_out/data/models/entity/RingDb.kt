package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.SpeechKit

interface RingDb: Data {
    val idRing: Long
    val partId: Long
    val idView: Long
    val speeches: List<SpeechDb>
    val exercises: List<ExerciseDb>
    val amount: Int
    val duration: Double
    val numberLaps: Int
//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int): Domain = object: Ring {
        override val idRing: Long = this@RingDb.idRing
        override val partId: Long = this@RingDb.partId
        override val idView: Long = this@RingDb.idView
        override val numberLaps: Int = this@RingDb.numberLaps
        override val amount: Int = this@RingDb.amount
        override val duration: Parameter = Parameter.fill(this@RingDb.duration, 2)
        override val speechKit: SpeechKit = SpeechKit.fill(this@RingDb.speeches.map{it.toDomain()})
        override val exercises: List<Exercise> = this@RingDb.exercises.map { it.toDomain() as Exercise }
    }
    companion object{
        fun fromDomain(domain: Domain): RingDb {
            return when (domain) {
                is Ring -> object: RingDb{
                    override val idRing: Long = domain.idRing
                    override val partId: Long = domain.partId
                    override val idView: Long = domain.idView
                    override val speeches: List<SpeechDb> = domain.speechKit.toList().map { SpeechDb.fromDomain(it) }
                    override val exercises: List<ExerciseDb> = domain.exercises.map { ExerciseDb.fromDomain(it) }
                    override val amount: Int = domain.amount
                    override val duration: Double = domain.duration.value
                    override val numberLaps: Int = domain.numberLaps
                }
                else -> EMPTY
            }
        }
        fun new(partID: Long = 0L) = object: RingDb{
            override val idRing: Long = 0
            override val partId: Long = partID
            override val idView: Long = 0
            override val speeches: List<SpeechDb> = emptyList()
            override val exercises: List<ExerciseDb> = emptyList()
            override val amount: Int = 2
            override val duration: Double = 0.0
            override val numberLaps: Int = 0
        }
        val EMPTY = object: RingDb{
            override val idRing: Long = 0
            override val partId: Long = 0
            override val idView: Long = 0
            override val speeches: List<SpeechDb> = emptyList()
            override val exercises: List<ExerciseDb> = emptyList()
            override val amount: Int = 0
            override val duration: Double = 0.0
            override val numberLaps: Int = 0
        }
    }
}