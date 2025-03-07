package com.example.count_out.entity.workout

import com.example.count_out.entity.workout.speech.SpeechKit

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