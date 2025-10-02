package com.count_out.presentation.screens.plans

import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Speech
import com.count_out.presentation.screens.prime.Event

sealed class PlansEvent: Event {
    data object Gets: PlansEvent()
    data class Run(val item: Plan): PlansEvent()
    data class Edit(val item: Long): PlansEvent()
    data class Del(val item: Plan) : PlansEvent()
    data object Add : PlansEvent()
    data class Copy(val item: Plan) : PlansEvent()
//    data class Update(val item: Plan) : PlansEvent()
    data class Select(val item: Plan) : PlansEvent()
    data class UpdateSpeech(val item: Speech) : PlansEvent()
    data object BackScreen : PlansEvent()
}