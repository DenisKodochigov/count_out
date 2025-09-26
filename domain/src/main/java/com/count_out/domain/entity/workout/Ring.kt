package com.count_out.domain.entity.workout

interface Ring: Element {
    val idRing: Long
    val partId: Long
    val speechId: Long
    val numberLaps: Int
    val amount: Int
    val duration: Parameter
    val speechKit: SpeechKit
    val exercises: List<Exercise>
}
