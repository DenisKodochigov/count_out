package com.example.count_out.ui.screens.exercise.view_component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.Exercise
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.exercise.exerciseCollapsing
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.IconsCollapsingCopySpeechDel
import com.example.count_out.ui.view_components.TextApp

@Composable
fun SelectActivity(uiState: TrainingScreenState, exercise: Exercise)
{
    Row( verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
    ){
        IconSelectActivity(uiState)
        Spacer(modifier = Modifier.width(12.dp))
        TextApp(text = exercise.activity.name, style = interReg14)
        Spacer(modifier = Modifier.weight(1f))
        IconsCollapsingCopySpeechDel(
            onCopy = { uiState.onCopyExercise(uiState.training.idTraining, exercise.idExercise)},
            onSpeech = { uiState.showBottomSheetSpeech.value = true },
            onDel = { uiState.onDeleteExercise(uiState.training.idTraining, exercise.idExercise) },
            onCollapsing = { exerciseCollapsing(uiState, exercise)},
            wrap = uiState.listCollapsingExercise.value.find { it == exercise.idExercise } != null
        )
    }
}
@Composable
fun IconSelectActivity(uiState: TrainingScreenState)
{
    IconButton(modifier = Modifier.width(24.dp).height(24.dp),
        onClick = { uiState.showBottomSheetSelectActivity.value = true })
    { Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "") }
}

fun getIcon(collapsing: Boolean): Int = if (collapsing) R.drawable.ic_wrap1 else R.drawable.ic_wrap