package com.count_out.presentation.screens.trainings

import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.Event

sealed class TrainingsEvent: Event {
    data object Gets: TrainingsEvent()
    data class Run(val id: Long): TrainingsEvent()
    data class Edit(val id: Long): TrainingsEvent()
    data class Del(val training: Training) : TrainingsEvent()
    data class Copy(val training: Training) : TrainingsEvent()
    data class Select(val training: Training) : TrainingsEvent()
    data object BackScreen : TrainingsEvent()
}