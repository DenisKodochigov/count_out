//package com.count_out.app.entity.workout
//
//import com.count_out.app.entity.DistanceE
//import com.count_out.app.entity.GoalSet
//import com.count_out.app.entity.TimeE
//import com.count_out.app.entity.WeightE
//import com.count_out.app.entity.Zone
//import com.count_out.app.entity.speech.SpeechKit
//
//interface Set {
//    val idSet: Long
//    val name: String
//    val exerciseId: Long
//    val speechId: Long
//    val speech: SpeechKit
//    val goal: GoalSet
//    val weight: Int
//    val weightE: WeightE // В каких единицах показывать
//    val distance: Double
//    val distanceE: DistanceE  // В каких единицах показывать
//    val duration: Int
//    val durationE: TimeE  // В каких единицах показывать
//    val reps: Int // количество отстчетов
//    val intensity: Zone
//    val intervalReps: Double
//    val intervalDown: Int //замедление отчетов
//    val groupCount: String // Группы отстчетов
//    val timeRest: Int //Храним значение в секундах
//    val timeRestE: TimeE // В каких единицах показывать
//}
