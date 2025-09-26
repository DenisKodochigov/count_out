package com.count_out.data.models

interface ExerciseDb {
    val idExercise: Long
    val ringId: Long
    val idView: Int
    val activityId: Long
    val activity: ActivityDb?
    val speechId: Long
    val speeches: List<SpeechDb>
    val sets: List<SetDb>
    val amountSet: Int
    val duration: Double
}