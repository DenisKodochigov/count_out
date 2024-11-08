package com.example.count_out.ui.screens.trainings

import androidx.compose.runtime.Stable
import com.example.count_out.entity.workout.Training
import javax.inject.Singleton

@Singleton
data class TrainingsScreenState(
    @Stable var trainings: List<Training> = emptyList(),
    @Stable var editTraining: (Training) -> Unit = {},
    @Stable var onAddTraining: () -> Unit = {},
    @Stable var onDeleteTraining: (Long) -> Unit = {},
    @Stable var onCopyTraining: (Long) -> Unit = {},
    @Stable var onDismiss: () -> Unit = {},
    @Stable var onSelectItem: (Long) -> Unit = {},
    @Stable var onStartWorkout: (Long) -> Unit = {},
    @Stable var onOtherAction: (Training) -> Unit = {},
    @Stable var onSelect: (Training) -> Unit = {},
    @Stable var onClickTraining: (Long) ->Unit = {},
    @Stable var idImage: Int = 0,
    @Stable var screenTextHeader: String = "",
){
    fun selectedItem(): Long{
        val id = trainings.find { it.isSelected }?.idTraining ?: 0L
        return id
    }
}