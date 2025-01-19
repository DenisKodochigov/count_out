package com.example.domain.entity


import com.example.domain.entity.enums.DistanceUnit
import com.example.domain.entity.enums.Goal
import com.example.domain.entity.enums.TimeUnit
import com.example.domain.entity.enums.WeightUnit
import com.example.domain.entity.enums.Zone

interface Set {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechId: Long
    val speech: SpeechKit?
    val goal: Goal
    val weight: Int
    val weightU: WeightUnit// В каких единицах показывать
    val distance: Double
    val distanceU: DistanceUnit  // В каких единицах показывать
    val duration: Int
    val durationU: TimeUnit  // В каких единицах показывать
    val reps: Int // количество отстчетов
    val intensity: Zone
    val intervalReps: Double
    val intervalDown: Int //замедление отчетов
    val groupCount: String // Группы отстчетов
    val timeRest: Int //Храним значение в секундах
    val timeRestU: TimeUnit // В каких единицах показывать
}
