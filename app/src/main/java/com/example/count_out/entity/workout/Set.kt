package com.example.count_out.entity.workout

import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.WeightE
import com.example.count_out.entity.Zone
import com.example.count_out.entity.speech.SpeechKit

interface Set {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechId: Long
    val speech: SpeechKit
    val goal: GoalSet
    val weight: Int
    val weightE: WeightE // В каких единицах показывать
    val intensity: Zone
    val distance: Double
    val distanceE: DistanceE  // В каких единицах показывать
    val duration: Double
    val durationE: TimeE  // В каких единицах показывать
    val reps: Int // количество отстчетов
    val intervalReps: Double
    val intervalDown: Int //замедление отчетов
    val groupCount: String // Группы отстчетов
    val timeRest: Int //Храним значение в секундах
    val timeRestE: TimeE // В каких единицах показывать
}
