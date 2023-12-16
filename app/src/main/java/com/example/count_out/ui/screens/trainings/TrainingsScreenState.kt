package com.example.count_out.ui.screens.trainings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.Training

data class TrainingsScreenState(
    val trainings: MutableState<List<Training>> = mutableStateOf(emptyList()),
    val triggerRunOnClickFAB: MutableState<Boolean> = mutableStateOf(false),
    @Stable var changeNameTraining: (Long) -> Unit = {},
    @Stable var editTraining: (Training) -> Unit = {},
    @Stable var deleteTraining: (Long) -> Unit = {},
    @Stable var onCopyTraining: (Long) -> Unit = {},
    @Stable var onAddClick: (String) -> Unit = {},
    @Stable var onDismiss: () -> Unit = {},
    @Stable var onSelectItem: (Long) -> Unit = {},
    @Stable var onOtherAction: (Training) -> Unit = {},
    @Stable var onSelect: (Training) -> Unit = {},
    @Stable var onClickTraining: (Long) ->Unit = {},
    @Stable var idImage: Int = 0,
    @Stable var screenTextHeader: String = "",
)