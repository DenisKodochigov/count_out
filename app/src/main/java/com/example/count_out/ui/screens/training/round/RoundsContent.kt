package com.example.count_out.ui.screens.training.round

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.Const.contourHor
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RoundType
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.exercise.ListExercises
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.icons.IconsCollapsing
import com.example.count_out.ui.view_components.icons.IconsGroup
import kotlin.math.roundToInt

@Composable
fun Round(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    roundType.value.amount = amountExercise(uiState, roundType)
    roundType.value.duration = durationRound(uiState, roundType)
    Frame(color = MaterialTheme.colorScheme.surfaceContainerHigh, contour = contourHor){
        Column( modifier = Modifier.padding(start = 6.dp, bottom = 4.dp, top = 4.dp)){
            Row1Round(uiState = uiState, roundType)
            Row2Round(uiState = uiState, roundType)
        }
    }
}
@Composable fun Row1Round(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    val nameNewSet = stringResource(id = R.string.set)
    Row( verticalAlignment = Alignment.CenterVertically ){
        IconsCollapsing(
            onClick = { setCollapsing(uiState, roundType) },
            wrap = getCollapsing(uiState, roundType) )
        Spacer(modifier = Modifier.width(2.dp))
        TextApp(
            text = stringResource(id = roundType.value.strId),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f))
        Column {
            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${ stringResource(id = R.string.exercises) }: ${roundType.value.amount}" +
                         " / ${roundType.value.duration} ${ stringResource(id = R.string.min)}",) }
        IconsGroup(
            onClickSpeech = {showSpeechRound(uiState, roundType)},
            onClickAddExercise = {
                uiState.onAddExercise(getIdRound(uiState, roundType), SetDB(name = nameNewSet))})
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun Row2Round(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    ListExercises(
        uiState = uiState,
        roundType = roundType.value,
        modifier = Modifier.padding(end = 8.dp),
        showExercises = getCollapsing(uiState, roundType) && roundType.value.amount > 0)
}

fun showSpeechRound(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    when (roundType.value) {
        RoundType.UP -> uiState.showSpeechWorkUp.value = true
        RoundType.OUT -> uiState.showSpeechWorkOut.value = true
        RoundType.DOWN -> uiState.showSpeechWorkDown.value = true
    }
}
fun amountExercise(uiState: TrainingScreenState, roundType: MutableState<RoundType>) =
    uiState.training.rounds.find{ it.roundType == roundType.value }?.exercise?.size ?: 0

fun durationRound(uiState: TrainingScreenState, roundType: MutableState<RoundType>): Int{
    var durationRound = 0.0
    uiState.training.rounds.find{ it.roundType == roundType.value }?.exercise?.forEach { exercise ->
        exercise.sets.forEach { set->
            durationRound += when (set.goal){
                GoalSet.DURATION-> set.duration * 60
                GoalSet.COUNT-> set.reps * set.intervalReps
                GoalSet.COUNT_GROUP -> set.reps * set.intervalReps
                GoalSet.DISTANCE -> set.distance * 600
            } + set.timeRest
        }
    } ?: 0
    return ( durationRound / 60).roundToInt()
}
//    uiState.training.rounds.find{ it.roundType == roundType }?.exercise?.size ?: 0
fun setCollapsing(uiState: TrainingScreenState, roundType: MutableState<RoundType>) {
    if (roundType.value.amount > 0) {
        when (roundType.value) {
            RoundType.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
            RoundType.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
            RoundType.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
        }
    }
}
fun getCollapsing(uiState: TrainingScreenState, roundType: MutableState<RoundType>): Boolean {
    return when (roundType.value) {
        RoundType.UP -> uiState.workUpCollapsing.value
        RoundType.OUT -> uiState.workOutCollapsing.value
        RoundType.DOWN -> uiState.workDownCollapsing.value
    }
}
fun getIdRound(uiState: TrainingScreenState, roundType: MutableState<RoundType>) =
    uiState.training.rounds.find { it.roundType == roundType.value }?.idRound ?: 0