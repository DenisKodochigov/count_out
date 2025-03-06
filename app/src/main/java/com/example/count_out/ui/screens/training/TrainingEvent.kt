package com.example.count_out.ui.screens.training

import com.example.count_out.entity.workout.Activity
import com.example.count_out.entity.workout.ActionWithActivity
import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.DataForChangeSequence
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.screens.prime.Event

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

    data class AddSet(val item: ActionWithSet): TrainingEvent()
    data class CopySet(val item: ActionWithSet): TrainingEvent()
    data class DeleteSet(val item: ActionWithSet): TrainingEvent()
    data class ChangeSet(val item: ActionWithSet): TrainingEvent()
    data object BackScreen : TrainingEvent()
}