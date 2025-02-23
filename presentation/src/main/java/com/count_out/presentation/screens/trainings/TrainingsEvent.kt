package com.count_out.presentation.screens.trainings

import com.count_out.app.presentation.prime.Event
import com.count_out.domain.entity.Training

sealed class TrainingsEvent: Event{
    data object Add: TrainingsEvent()
    data object Gets: TrainingsEvent()
    data class Run(val id: Long): TrainingsEvent()
    data class Edit(val id: Long): TrainingsEvent()
    data class Del(val training: Training) : TrainingsEvent()
    data class Copy(val training: Training) : TrainingsEvent()
    data class Select(val training: Training) : TrainingsEvent()
    data object BackScreen : TrainingsEvent()
}