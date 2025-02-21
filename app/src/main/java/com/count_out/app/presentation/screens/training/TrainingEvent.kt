package com.count_out.app.presentation.screens.training

import com.count_out.app.presentation.prime.Event
import com.count_out.app.presentation.models.ActionWithActivityImpl
import com.count_out.app.presentation.models.ActionWithSetImpl
import com.count_out.app.presentation.models.DataForChangeSequenceImpl
import com.count_out.domain.entity.Activity
import com.count_out.domain.entity.Exercise
import com.count_out.domain.entity.Training

sealed class TrainingEvent: Event{
    data class GetTraining(val id: Long): TrainingEvent()
    data class DelTraining(val training: Training) : TrainingEvent()
    data class ChangeNameTraining(val training: Training) : TrainingEvent()
    data class AddExercise(val exercise: Exercise): TrainingEvent()
    data class CopyExercise(val exercise: Exercise): TrainingEvent()
    data class DelExercise(val exercise: Exercise): TrainingEvent()
    data class ChangeSequenceExercise(val item: DataForChangeSequenceImpl): TrainingEvent()

    data class SelectActivity(val activity: ActionWithActivityImpl): TrainingEvent()
    data class SetColorActivity(val activity: Activity): TrainingEvent()
    data class ChangeActivity(val activity: Activity): TrainingEvent()

    data class AddSet(val item: ActionWithSetImpl): TrainingEvent()
    data class CopySet(val item: ActionWithSetImpl): TrainingEvent()
    data class DeleteSet(val item: ActionWithSetImpl): TrainingEvent()
    data class ChangeSet(val item: ActionWithSetImpl): TrainingEvent()
    data object BackScreen : TrainingEvent()
}