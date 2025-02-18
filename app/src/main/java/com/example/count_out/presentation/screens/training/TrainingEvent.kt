package com.example.count_out.presentation.screens.training

import com.example.count_out.presentation.prime.Event
import com.example.count_out.presentation.models.ActionWithActivityImpl
import com.example.count_out.presentation.models.ActionWithSetImpl
import com.example.count_out.presentation.models.DataForChangeSequenceImpl
import com.example.domain.entity.Activity
import com.example.domain.entity.Exercise
import com.example.domain.entity.Training

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