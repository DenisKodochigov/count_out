package com.count_out.presentation.screens.trainings

import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.Event

sealed class TrainingsEvent: Event {
    data object Gets: TrainingsEvent()
    data class Run(val item: Long): TrainingsEvent()
    data class Edit(val item: Long): TrainingsEvent()
    data class Del(val item: Training) : TrainingsEvent()
    data class Copy(val item: Training) : TrainingsEvent()
    data class Update(val item: Training) : TrainingsEvent()
    data class Select(val item: Training) : TrainingsEvent()
    data class UpdateSpeech(val item: SpeechKit) : TrainingsEvent()
    data object BackScreen : TrainingsEvent()
}