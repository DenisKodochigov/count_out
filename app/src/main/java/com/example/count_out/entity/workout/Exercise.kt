package com.example.count_out.entity.workout

import com.example.count_out.entity.Activity
import com.example.count_out.entity.SpeechKit

interface Exercise {
    val idExercise: Long
    val roundId: Long
    val activity: Activity
    val activityId: Long
    val speechId: Long
    val speech: SpeechKit
    val sets: List<Set>
    val sequenceNumber: Int
}