package com.example.count_out.entity.workout

import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.SpeechKit
import com.example.count_out.entity.Zone

interface Set {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechId: Long
    val speech: SpeechKit
    val goal: GoalSet
    val weight: Int
    val intensity: Zone
    val distance: Double
    val duration: Double
    val reps: Int // количество отстчетов
    val intervalReps: Double
    val intervalDown: Int //замедление отчетов
    val groupCount: String // Группы отстчетов
    val timeRest: Int
}
