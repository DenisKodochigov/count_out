package com.example.count_out.entity.workout

import com.example.count_out.entity.enums.Goal
import com.example.count_out.entity.enums.Zone
import com.example.count_out.entity.workout.speech.SpeechKit

interface Set {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechId: Long
    val speech: SpeechKit?
    val goal: Goal
    val weight: Parameter
    val distance: Parameter
    val duration: Parameter
    val reps: Int // количество отстчетов
    val intensity: Zone
    val intervalReps: Double
    val intervalDown: Int //замедление отчетов
    val groupCount: String // Группы отстчетов
    val rest: Parameter //Храним значение в секундах
}
