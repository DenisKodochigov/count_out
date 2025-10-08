package com.count_out.domain.entity.workout

interface Exercise: Domain {
     val idExercise: Long
     val ringId: Long
     val idView: Int
     val activity: Activity?
     val activityId: Long
     val speechKit: SpeechKit
     val sets: List<Set>
     val amountSet: Int
     val duration: Parameter
}