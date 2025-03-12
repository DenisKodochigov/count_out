package com.count_out.presentation.screens.training

import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.ActionWithActivity
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.screens.prime.Event

sealed class TrainingEvent: Event {
    data class GetTraining(val id: Long): TrainingEvent()
    data class DelTraining(val training: Training) : TrainingEvent()
    data class UpdateTraining(val training: Training) : TrainingEvent()
    data class AddExercise(val exercise: Exercise): TrainingEvent()
    data class CopyExercise(val exercise: Exercise): TrainingEvent()
    data class DelExercise(val exercise: Exercise): TrainingEvent()
    data class ChangeSequenceExercise(val item: DataForChangeSequence): TrainingEvent()

    data class SelectActivity(val activity: ActionWithActivity): TrainingEvent()
    data class SetColorActivity(val activity: Activity): TrainingEvent()
    data class UpdateActivity(val activity: Activity): TrainingEvent()

    data class AddSet(val item: Set): TrainingEvent()
    data class CopySet(val item: Set): TrainingEvent()
    data class DeleteSet(val item: Set): TrainingEvent()
    data class ChangeSet(val item: Set): TrainingEvent()
    data object BackScreen : TrainingEvent()
}