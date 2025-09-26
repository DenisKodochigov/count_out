package com.count_out.data.models

interface SetDb {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechId: Long
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
}