package com.count_out.data.models

interface RingDb: Data {
    val idRing: Long
    val partId: Long
    val speeches: List<SpeechDb>
    val exercises: List<ExerciseDb>
    val amount: Int
    val duration: Double
    val numberLaps: Int
}