package com.count_out.presentation.screens.training

import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.Event

sealed class TrainingEvent: Event {
    data class GetTraining(val id: Long): TrainingEvent()
    data class DelTraining(val training: Training) : TrainingEvent()
    data class UpdateTraining(val training: Training) : TrainingEvent()
    data class CopyExercise(val exercise: Exercise): TrainingEvent()
    data class DelExercise(val exercise: Exercise): TrainingEvent()
    data class ChangeSequenceExercise(val item: DataForChangeSequence): TrainingEvent()

    data class CopySet(val item: Set): TrainingEvent()
    data class DeleteSet(val item: Set): TrainingEvent()
    data class UpdateSet(val item: Set): TrainingEvent()

    data class ShowBSSpeechTraining(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechWorkUp(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechWorkOut(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechWorkDown(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechExercise(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()
    data class ShowBSSelectActivity(val item: Boolean): TrainingEvent()

    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()
    data class ShowBSSpeechSet(val item: Boolean): TrainingEvent()



    data object BackScreen : TrainingEvent()
}