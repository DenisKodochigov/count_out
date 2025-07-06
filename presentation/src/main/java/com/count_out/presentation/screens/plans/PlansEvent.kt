package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.Event

sealed class PlansEvent: Event {
    data object Gets: PlansEvent()
    data class Run(val item: Long): PlansEvent()
    data class Edit(val item: Long): PlansEvent()
    data class Del(val item: Training) : PlansEvent()
    data class Copy(val item: Training) : PlansEvent()
    data class Update(val item: Training) : PlansEvent()
    data class Select(val item: Training) : PlansEvent()
    data class UpdateSpeech(val item: SpeechKit) : PlansEvent()
    data object BackScreen : PlansEvent()
}