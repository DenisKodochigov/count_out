package com.example.count_out.entity

interface Set {
    val idSet: Long
    val name: String
    val exerciseId: Long
    val speechId: Long
    val speech: Speech

    val weight: Int
    val intensity: String
    val distance: Double
    val duration: Int
    val reps: Int // количество отстчетов
    val intervalReps: Int
    val intervalDown: Int //замедление отчетов
    val groupCount: String // Группы отстчетов
    val timeRest: Int
}
