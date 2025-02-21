package com.count_out.domain.entity

interface Ring {
    val idRing: Long
    var name: String
    val countRing: Int
    val trainingId: Long
    val speechId: Long
    val speech: SpeechKit?
    val exercise: List<Exercise>
    val amount: Int
    val duration: Parameter
}