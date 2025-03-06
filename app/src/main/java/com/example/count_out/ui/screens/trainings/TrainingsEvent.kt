package com.example.count_out.ui.screens.trainings


import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.screens.prime.Event

sealed class TrainingsEvent: Event {
    data object Add: TrainingsEvent()
    data object Gets: TrainingsEvent()
    data class Run(val id: Long): TrainingsEvent()
    data class Edit(val id: Long): TrainingsEvent()
    data class Del(val training: Training) : TrainingsEvent()
    data class Copy(val training: Training) : TrainingsEvent()
    data class Select(val training: Training) : TrainingsEvent()
    data object BackScreen : TrainingsEvent()
}