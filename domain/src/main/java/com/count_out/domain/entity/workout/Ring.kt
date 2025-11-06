package com.count_out.domain.entity.workout

interface Ring: Domain {
    val idRing: Long
    val partId: Long
    val numberLaps: Int
    val amount: Int
    val duration: Parameter
    val speechKit: SpeechKit
    val exercises: List<Exercise>
    companion object{
        fun default(partId: Long) = object: Ring{
            override val idRing: Long = 0
            override val partId: Long = partId
            override val numberLaps: Int = 0
            override val amount: Int = 2
            override val duration: Parameter = Parameter.EMPTY
            override val speechKit: SpeechKit = SpeechKit.EMPTY
            override val exercises: List<Exercise> = emptyList()

        }
        fun Ring.amount(value: Int) = object: Ring{
            override val idRing: Long = this@amount.idRing
            override val partId: Long = this@amount.partId
            override val numberLaps: Int = this@amount.numberLaps
            override val amount: Int = value
            override val duration: Parameter = this@amount.duration
            override val speechKit: SpeechKit = this@amount.speechKit
            override val exercises: List<Exercise> = this@amount.exercises

        }
    }
}
